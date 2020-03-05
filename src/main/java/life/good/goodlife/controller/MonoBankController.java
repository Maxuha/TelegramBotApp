package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import life.good.goodlife.component.UserHistoryComponent;
import life.good.goodlife.service.bot.CommandService;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.monobank.BalanceService;
import life.good.goodlife.service.monobank.CurrencyService;

@BotController
public class MonoBankController {
    private final UserHistoryComponent userHistoryComponent;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final BalanceService balanceService;
    private final CommandService commandService;

    public MonoBankController(UserHistoryComponent userHistoryComponent, UserService userService, CurrencyService currencyService, BalanceService balanceService, CommandService commandService) {
        this.userHistoryComponent = userHistoryComponent;
        this.userService = userService;
        this.currencyService = currencyService;
        this.balanceService = balanceService;
        this.commandService = commandService;
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
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "Банкинг");
        String msg = commandService.findCommandsByName("Банкинг").getFullDescription();
        SendMessage sendMessage = new SendMessage(chatId, msg);
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Мой баланс"),
                        new KeyboardButton("Курс валют"),
                        new KeyboardButton("Транзакции"),
                        new KeyboardButton("Главное меню")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }

    private SendMessage showBalance(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/balance");
        String msg = balanceService.balance();
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }

    private SendMessage showCurrency(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/currency");
        String msg = currencyService.currency();
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }
}
