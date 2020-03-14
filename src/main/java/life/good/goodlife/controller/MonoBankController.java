package life.good.goodlife.controller;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLImageElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLinkElement;
import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendSticker;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.bot.User;
import life.good.goodlife.model.buttons.Buttons;
import life.good.goodlife.model.monobonk.*;
import life.good.goodlife.service.bot.*;
import life.good.goodlife.service.monobank.*;
import life.good.goodlife.statics.Request;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@BotController
public class MonoBankController {
    private static Logger logger = LoggerFactory.getLogger(MonoBankController.class);
    private final UserHistoryService userHistoryService;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final BalanceService balanceService;
    private final CommandService commandService;
    private final LoginService loginService;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final StatementService statementService;

    public MonoBankController(UserHistoryService userHistoryService, UserService userService,
                              CurrencyService currencyService, BalanceService balanceService, CommandService commandService,
                              LoginService loginService, TelegramBotExecuteComponent telegramBotExecuteComponent, StatementService statementService) {
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.currencyService = currencyService;
        this.balanceService = balanceService;
        this.commandService = commandService;
        this.loginService = loginService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.statementService = statementService;
    }

    @BotRequest("/currency")
    BaseRequest getCurrency(Long chatId) {
        return showCurrency(chatId);
    }

    @BotRequest("Курс валют")
    BaseRequest getCurrencyBtn(Long chatId) {
        return showCurrency(chatId);
    }

    @BotRequest("/balance")
    BaseRequest getBalance(Long chatId) {
        return showChooseCart(chatId);
    }

    @BotRequest("Мой баланс")
    BaseRequest getBalanceBtn(Long chatId) {
        return showChooseCart(chatId);
    }

    @BotRequest("Банкинг")
    BaseRequest bankBtn(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "Банкинг", "");
        String token = loginService.getToken(user.getId());
        String msg = "Перейдите по адресу https://api.monobank.ua/ и скопируйте токен, вставьте его командой /set_mono_token {token}";
        SendMessage sendMessage = new SendMessage(chatId, msg);
        if (token == null) {
            return sendMessage;
        }
        return showMonoBankMenu(chatId);
    }

    @BotRequest("/set_mono_token **")
    BaseRequest setToken(Long chatId, String text) {
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), "/set_mono_token", "");
        String token = text.split(" ")[1];
        logger.info("Creating user monobank");
        UserInfo userInfo = loginService.getUserInfo(token);
        UserMonobank userMonobank = new UserMonobank();
        userMonobank.setUserId(userService.findByChatId(chatId).getId());
        userMonobank.setToken(token);
        userMonobank.setClientId(userInfo.getClientId());
        userMonobank.setName(userInfo.getName());
        loginService.createUser(userMonobank);
        Account[] accounts = userInfo.getAccounts();
        for (Account account : accounts) {
            account.setClientId(userInfo.getClientId());
            loginService.createAccount(account);
        }
        return showMonoBankMenu(chatId);
    }
    @BotRequest("Синхроннизация выписки")
    BaseRequest synchronizeBtn(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "Синхроннизация выписки", "");
        String token = loginService.getToken(user.getId());
        String msg = "Синхроннизация...";
        telegramBotExecuteComponent.sendMessage(new SendMessage(chatId, msg));
        String accountId = "cF0-POVN4umkmK1vtoPXzw";
        Long seconds = statementService.getLastTimeByAccountId(accountId);
        if (seconds == null || seconds == 0) {
            seconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        }
        statementService.createStatementList(token, accountId, seconds);
        msg = "Синхроннизация успешна";
        return new SendMessage(chatId, msg);
    }

    @BotRequest("\uD83D\uDCB3 **")
    BaseRequest chooseCartBtn(Long chatId, String text) {
        String[] result = text.split(" ");
        String[] cart = new String[4];
        for (int i = 3; i < result.length; i++) {
            cart[i-3] = result[i];
        }
        return showBalance(chatId, cart);
    }

    private SendMessage showMonoBankMenu(Long chatId) {
        logger.info("Opening monobank menu");
        String msg = commandService.findCommandsByName("Банкинг").getFullDescription();
        SendMessage sendMessage = new SendMessage(chatId, msg);
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Мой баланс"),
                        new KeyboardButton("Курс валют"),
                        new KeyboardButton("Выписка"),
                        new KeyboardButton("Контроль расходами"),
                        new KeyboardButton("Синхроннизация выписки"),
                        new KeyboardButton("Главное меню")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }

    private SendMessage showChooseCart(Long chatId) {
        User user = userService.findByChatId(chatId);
        UserMonobank userMonobank = loginService.getByUserId(user.getId());
        List<Account> accounts = loginService.getAllAccountByClientId(userMonobank.getClientId());
        String[][] accountButtons = new String[accounts.size() + 1][1];
        int index = 0;
        StringBuffer cart;
        for (Account account : accounts) {
            for (int i = 0; i < account.getMaskedPan().length; i++) {
                cart = new StringBuffer(account.getMaskedPan()[i]);
                cart.replace(6, 6, "*****");
                cart.insert(4, " ");
                cart.insert(9, " ");
                cart.insert(14, " ");
                accountButtons[index][0] = "\uD83D\uDCB3 " + MonobankFactory.getNameTypeCartByType(account.getType()) +
                        ", " + CurrencyCodeFactory.getCartCurrencyNameByCurrencyCode(account.getCurrencyCode()) + " " + cart.toString();
                index++;
            }
        }
        accountButtons[accounts.size()][0] = Buttons.mainButton[1];
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(accountButtons).resizeKeyboard(true);
        SendMessage sendMessage = new SendMessage(chatId, "Выбери карту");
        sendMessage.replyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    private SendMessage showBalance(Long chatId, String[] cartFull) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "/balance", "");
        StringBuilder cart = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cartFull.length; i++) {
            cart.append(cartFull[i]);
            result.append(cartFull[i]).append("%20");
        }
        cart.delete(6, 11);
        Account account = balanceService.getBalance(new String[] {cart.toString()});
        /*InputStream ismain = MonoBankController.class.getClassLoader().getResourceAsStream("image/BackgroundCat.png");
        BufferedImage read = null;
        try {
            assert ismain != null;
            read = ImageIO.read(ismain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert read != null;
        Graphics g = read.getGraphics();
        //g.setFont("Calibri");
        g.setColor(Color.WHITE);
        g.drawString("Hello world ",7, 55);
        g.dispose();
        File file = new File("image.png");
        try {
            ImageIO.write(read, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //com.gargoylesoftware.htmlunit.javascript.host.fetch.Request request =
        WebClient webClient = new WebClient();
        URL url = null;
        try {
            url = new URL("http://jump-to-infinity.com/index5.php?cart=" + result.toString());
        } catch (MalformedURLException e) {
            logger.error("Incorrectly url - " + e.getMessage());
        }
        WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);
        requestSettings.setAdditionalHeader("Content-Type", "text/javascript");
        webClient.getOptions().setCssEnabled(true);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        try {
            HtmlPage page = webClient.getPage(requestSettings);
            webClient.waitForBackgroundJavaScript(10000);
            String link = page.getElementById("download").getAttribute("href");
            System.out.println("link: " + link);
            String[] strings = link.split(",");;
            //byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
            byte[] data = Base64.decodeBase64(strings[1].getBytes());
            OutputStream outputStream = new FileOutputStream("image1.png");
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            File file = new File("image1.png");
            telegramBotExecuteComponent.sendSticker(new SendSticker(chatId, file));
        } catch (IOException e) {
            logger.error("Error page");
        } finally {
            webClient.close();
        }
        //Request.get("http://jump-to-infinity.com/index5.php?cart=" + result.toString().trim());
        /*String result = "<b>Мой баланс: </b>\n\n" + "Карта: " + cart + "\n" +
                "Тип: " + account.getType() + "\n" +
                "Баланс: " + Balance.getBalanceFactory(account.getBalance(), account.getCurrencyCode()) + "\n" +
                "Кредитный лимит: " + Balance.getBalanceFactory(account.getCreditLimit(), account.getCurrencyCode()) + "\n";*/
        //new SendMessage(chatId, result).parseMode(ParseMode.HTML).disableWebPagePreview(true)
        return null;
    }

    private SendMessage showCurrency(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "/currency", "");
        String msg = currencyService.currency(loginService.getToken(user.getId()));
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }
}
