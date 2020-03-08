package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.model.bot.Command;
import life.good.goodlife.model.bot.User;
import life.good.goodlife.model.monobonk.Account;
import life.good.goodlife.model.monobonk.UserInfo;
import life.good.goodlife.model.monobonk.UserMonobank;
import life.good.goodlife.service.bot.*;
import life.good.goodlife.service.monobank.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@BotController
public class MonoBankController {
    private static Logger logger = LoggerFactory.getLogger(MonoBankController.class);
    private final UserHistoryService userHistoryService;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final BalanceService balanceService;
    private final CommandService commandService;
    private final LoginService loginService;

    public MonoBankController(UserHistoryService userHistoryService, UserService userService,
                              CurrencyService currencyService, BalanceService balanceService, CommandService commandService,
                              LoginService loginService) {
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.currencyService = currencyService;
        this.balanceService = balanceService;
        this.commandService = commandService;
        this.loginService = loginService;
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
        return showBalance(chatId);
    }

    @BotRequest("Мой баланс")
    BaseRequest getBalanceBtn(Long chatId) {
        return showBalance(chatId);
    }

    @BotRequest("Банкинг")
    BaseRequest bankBtn(Long chatId) {
        User user = userService.findByChatId(chatId);
        logger.info("Find command: 'Банкинг'");
        Command command = commandService.findCommandsByName("Банкинг");
        logger.info("Creating history command 'Банкинг'");
        userHistoryService.createUserHistory(user.getId(), command);
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
        logger.info("Find command: '/set_mono_token'");
        Command command = commandService.findCommandsByName("/set_mono_token");
        logger.info("Creating history command '/set_mono_token'");
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), command);
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
            loginService.createAccount(account);
        }
        return showMonoBankMenu(chatId);
    }

    private SendMessage showMonoBankMenu(Long chatId) {
        logger.info("Openning monobank menu");
        String msg = commandService.findCommandsByName("Банкинг").getFullDescription();
        SendMessage sendMessage = new SendMessage(chatId, msg);
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Мой баланс"),
                        new KeyboardButton("Курс валют"),
                        new KeyboardButton("Выписка"),
                        new KeyboardButton("Контроль расходами"),
                        new KeyboardButton("Главное меню")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }

    private SendMessage showBalance(Long chatId) {
        User user = userService.findByChatId(chatId);
        logger.info("Find command: '/balance'");
        Command command = commandService.findCommandsByName("/balance");
        logger.info("Creating history command '/balance'");
        userHistoryService.createUserHistory(user.getId(), command);
        String msg = balanceService.balance(loginService.getToken(user.getId()));
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }

    private SendMessage showCurrency(Long chatId) {
        User user = userService.findByChatId(chatId);
        logger.info("Find command: '/currency'");
        Command command = commandService.findCommandsByName("/currency");
        logger.info("Creating history command '/currency'");
        userHistoryService.createUserHistory(user.getId(), command);
        String msg = currencyService.currency(loginService.getToken(user.getId()));
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }
}
