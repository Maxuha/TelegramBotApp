package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.UserHistoryComponent;
import life.good.goodlife.model.bot.User;
import life.good.goodlife.model.monobonk.Account;
import life.good.goodlife.model.monobonk.UserInfo;
import life.good.goodlife.model.monobonk.UserMonobank;
import life.good.goodlife.service.bot.CommandService;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.monobank.BalanceService;
import life.good.goodlife.service.monobank.CurrencyService;
import life.good.goodlife.service.monobank.LoginService;

@BotController
public class MonoBankController {
    private final UserHistoryComponent userHistoryComponent;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final BalanceService balanceService;
    private final CommandService commandService;
    private final LoginService loginService;

    public MonoBankController(UserHistoryComponent userHistoryComponent, UserService userService, CurrencyService currencyService, BalanceService balanceService, CommandService commandService, LoginService loginService) {
        this.userHistoryComponent = userHistoryComponent;
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
        userHistoryComponent.createUserHistory(user.getId(), "Банкинг");
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
        String token = text.split(" ")[1];
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
        userHistoryComponent.createUserHistory(user.getId(), "/balance");
        String msg = balanceService.balance(loginService.getToken(user.getId()));
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }

    private SendMessage showCurrency(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryComponent.createUserHistory(user.getId(), "/currency");
        String msg = currencyService.currency(loginService.getToken(user.getId()));
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }
}
