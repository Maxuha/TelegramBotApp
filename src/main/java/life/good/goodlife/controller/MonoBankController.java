package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.google.gson.Gson;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendSticker;
import life.good.goodlife.component.MonoBankComponent;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.bot.User;
import life.good.goodlife.model.buttons.Buttons;
import life.good.goodlife.model.monobonk.*;
import life.good.goodlife.service.bot.*;
import life.good.goodlife.service.monobank.*;
import life.good.goodlife.statics.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final MonoBankComponent monoBankComponent;

    public MonoBankController(UserHistoryService userHistoryService, UserService userService,
                              CurrencyService currencyService, BalanceService balanceService, CommandService commandService,
                              LoginService loginService, TelegramBotExecuteComponent telegramBotExecuteComponent, StatementService statementService, MonoBankComponent monoBankComponent) {
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.currencyService = currencyService;
        this.balanceService = balanceService;
        this.commandService = commandService;
        this.loginService = loginService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.statementService = statementService;
        this.monoBankComponent = monoBankComponent;
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
        return monoBankComponent.showMonoBankMenu(chatId);
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
        Map<String, String> body = new HashMap<>();
        body.put("raw", "{\"webHookUrl\": \"https://goodlifeapplication.herokuapp.com/webhook/monobank\"}");
        Map<String, String> header = new HashMap<>();
        header.put("X-Token", token);
        String response = Request.post("https://api.monobank.ua/personal/webhook", header, body, "raw");
        ResponseWebhook responseWebhook = new Gson().fromJson(response, ResponseWebhook.class);
        if ("ok".equals(responseWebhook.getStatus())) {
            logger.info("Notification monobank is ok {}", chatId);
            InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                    new InlineKeyboardButton[]{
                            new InlineKeyboardButton("Отменить").callbackData("notificationMonoBankOff"),
                    });
            SendMessage sendMessage = new SendMessage(chatId, "Оповещения о платежах установлены");
            sendMessage.replyMarkup(inlineKeyboard);
            telegramBotExecuteComponent.sendMessage(sendMessage);
        }
        return monoBankComponent.showMonoBankMenu(chatId);
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
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), "/select_cart", "");
        String[] result = text.split(" ");
        String[] cart = new String[4];
        for (int i = 3; i < result.length; i++) {
            cart[i-3] = result[i];
        }
        return showBalance(chatId, cart);
    }

    private SendMessage showChooseCart(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "/balance", "");
        UserMonobank userMonobank = loginService.getByUserId(user.getId());
        List<Account> accounts = loginService.getAllAccountByClientId(userMonobank.getClientId());
        String[][] accountButtons = new String[accounts.size()+1][2];
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
                accountButtons[index][1] = "";
                index++;
            }
        }

        accountButtons[accounts.size()][0] = Buttons.mainButton[0];
        accountButtons[accounts.size()][1] = Buttons.mainButton[1];
        System.out.println(Arrays.toString(accountButtons));
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                accountButtons).resizeKeyboard(true);
        SendMessage sendMessage = new SendMessage(chatId, "Выбери карту");
        sendMessage.replyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    private SendSticker showBalance(Long chatId, String[] cartPartFull) {
        StringBuilder cart = new StringBuilder();
        StringBuilder cartFull = new StringBuilder();
        for (int i = 0; i < cartPartFull.length; i++) {
            cart.append(cartPartFull[i]);
            cartFull.append(cartPartFull[i]).append(" ");
        }
        cart.delete(6, 11);
        Account account = balanceService.getBalance(new String[] {cart.toString()});
        BufferedImage image = getStickerBalance(cartFull.toString(), Balance.getBalanceFactory(account.getBalance(),
                account.getCurrencyCode()).toString(), Balance.getBalanceFactory(account.getCreditLimit(), account.getCurrencyCode()).toString(),
                account.getType(), account.getCurrencyCode());
        //File outputFile = new File("image15645.png");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[0];
        try {
            ImageIO.write(image, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*String result = "<b>Мой баланс: </b>\n\n" + "Карта: " + cart + "\n" +
                "Тип: " + account.getType() + "\n" +
                "Баланс: " + Balance.getBalanceFactory(account.getBalance(), account.getCurrencyCode()) + "\n" +
                "Кредитный лимит: " + Balance.getBalanceFactory(account.getCreditLimit(), account.getCurrencyCode()) + "\n";
        return new SendMessage(chatId, result).parseMode(ParseMode.HTML).disableWebPagePreview(true);*/
        return new SendSticker(chatId, bytes);
    }

    private BufferedImage getStickerBalance(String cart, String balance, String creditLimit, String type, int currencyCode)  {
        int cartX = 30;
        int cartY = 165;
        int balanceX = 30;
        int balanceY = 110;
        int creditX = 30;
        int creditY = 240;
        int modX = 370;
        int modY = 45;
        Color color;
        String pathToCart = "image/";
        switch (type) {
            case "white":
                color = new Color(0, 0, 0);
                pathToCart += "WhiteCart.png";
                break;
            case "platinum":
                color = new Color(0);
                pathToCart += "PlatinumCart.png";
                break;
            case "gold":
                color = new Color(0);
                pathToCart += "GoldCart.png";
                break;
            default:
                color = new Color(255, 255, 255, 255);
                pathToCart += "BlackCart.png";
                break;
        }
        TextLayout textLayout;
        Font font = new Font("Arial", Font.PLAIN, 36);
        BufferedImage src = null;
        try {
            src = ImageIO.read(MonoBankController.class.getClassLoader().getResourceAsStream(pathToCart));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert src != null;
        BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g1d = image.createGraphics();
        setRenderingHints(g1d);
        g1d.setPaint(color);
        g1d.drawImage(src, 0, 0, null);
        textLayout = new TextLayout(cart, font, g1d.getFontRenderContext());
        textLayout.draw(g1d, cartX, cartY);
        font = new Font("Arial", Font.PLAIN, 28);
        textLayout = new TextLayout("Баланс: " + balance, font, g1d.getFontRenderContext());
        textLayout.draw(g1d, balanceX, balanceY);
        font = new Font("Arial", Font.PLAIN, 18);
        textLayout = new TextLayout("Кредитные: " + creditLimit, font, g1d.getFontRenderContext());
        textLayout.draw(g1d, creditX, creditY);
        if (currencyCode == 840) {
            g1d.setPaint(new Color(255, 255, 255, 128));
            textLayout = new TextLayout("USD", font, g1d.getFontRenderContext());
            textLayout.draw(g1d, modX, modY);
        } else if (currencyCode == 978){
            g1d.setPaint(new Color(255, 255, 255, 128));
            textLayout = new TextLayout("EUR", font, g1d.getFontRenderContext());
            textLayout.draw(g1d, modX, modY);
        }
        g1d.dispose();
        return image;
    }

    private void setRenderingHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    private SendSticker showCurrency(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "/currency", "");
        Currency[] currencies = currencyService.getCurrency();
       // StringBuilder msg = new StringBuilder("Курс валют\n\n            Покупка     Продажа\n");
        String flag;
        int index = 0;
            /*if (flag != null) {
                flag += "    " + String.format("%.2f", currency.getRateBuy()) + "         " +
                        String.format("%.2f", currency.getRateSell());
            } else {
                flag = "";
            }
            msg.append(flag).append("\n");*/
        BufferedImage image = getStickerCurrency(currencies);
        byte[] bytes = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SendSticker(chatId, bytes);
    }

    private BufferedImage getStickerCurrency(Currency[] currencies) {
        int buyX = 150;
        int buyY = 40;
        int sellX = 250;
        int sellY = 40;
        int index = 1;
        Color color = new Color(255, 255, 255);
        String pathToCart = "image/BackgroundCurrency.png";
        TextLayout[] textLayout = new TextLayout[15];
        Font font = new Font("Arial", Font.PLAIN, 18);
        BufferedImage src = null;
        try {
            src = ImageIO.read(MonoBankController.class.getClassLoader().getResourceAsStream(pathToCart));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert src != null;
        BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g1d = image.createGraphics();
        setRenderingHints(g1d);
        g1d.setPaint(color);
        g1d.drawImage(src, 0, 0, null);
        for (Currency currency : currencies) {
            buyY += 60;
            sellY += 60;
            textLayout[index - 1] = new TextLayout(String.format("%.4f", currency.getRateBuy()), font, g1d.getFontRenderContext());
            textLayout[index - 1].draw(g1d, buyX, buyY);
            textLayout[index - 1] = new TextLayout(String.format("%.4f", currency.getRateSell()), font, g1d.getFontRenderContext());
            textLayout[index - 1].draw(g1d, sellX, sellY);
            index++;
        }
        g1d.dispose();
        return image;
    }
}

//        /*float[] kernel = {
//                1f / 9f, 1f / 9f, 1f / 9f,
//                1f / 9f, 1f / 9f, 1f / 9f,
//                1f / 9f, 1f / 9f, 1f / 9f
//        };
//
//        ConvolveOp op =  new ConvolveOp(new Kernel(3, 3, kernel),
//                ConvolveOp.EDGE_NO_OP, null);*/
//        //op.filter(image, null)
