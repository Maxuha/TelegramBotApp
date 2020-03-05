package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.component.UserHistoryComponent;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.monobank.BalanceService;
import life.good.goodlife.service.monobank.CurrencyService;

@BotController
public class MonoBankController {
    private final UserHistoryComponent userHistoryComponent;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final BalanceService balanceService;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;

    public MonoBankController(UserHistoryComponent userHistoryComponent, UserService userService, CurrencyService currencyService, BalanceService balanceService, TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.userHistoryComponent = userHistoryComponent;
        this.userService = userService;
        this.currencyService = currencyService;
        this.balanceService = balanceService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }

    @BotRequest("/currency")
    BaseRequest getCurrency(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/currency");
        String msg = currencyService.currency();
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }

    @BotRequest("/balance")
    BaseRequest getBalance(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/balance");
        String msg = balanceService.balance();
        return new SendMessage(chatId, msg).parseMode(ParseMode.HTML).disableWebPagePreview(true);
    }

    @BotRequest("Банкинг")
    BaseRequest bankBtn(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "Банкинг");
        String msg = "...";
        BaseResponse baseResponse = telegramBotExecuteComponent.sendMessageWithResponse(chatId, msg);
        return new SendMessage(chatId, baseResponse.description());
    }

}
