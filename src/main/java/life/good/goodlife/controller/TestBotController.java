package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.github.telegram.mvc.api.MessageType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.List;

@BotController
public class TestBotController {
    @Autowired
    Environment environment;

    /*@BotRequest(messageType = MessageType.INLINE_QUERY)
    BaseRequest testInlineQuery(Long chatId, Update update) {
        System.out.println(chatId);
        TelegramBot bot = new TelegramBot(environment.getProperty("telegram.bot.token"));
        InlineQuery inlineQuery = update.inlineQuery();
        ChosenInlineResult chosenInlineResult = update.chosenInlineResult();
        System.out.println("res: " + chosenInlineResult);
        CallbackQuery callbackQuery = update.callbackQuery();
        System.out.println("callback: " + callbackQuery);
        InlineQueryResult r2 = new InlineQueryResultArticle(inlineQuery.id(), "title", "message text");
        BaseResponse response = bot.execute(new AnswerInlineQuery(inlineQuery.id(), r2));
        System.out.println(response.isOk() + " " + response.description());
        return null;
    }*/

    @BotRequest(messageType = MessageType.INLINE_CHOSEN)
    BaseRequest testInlineChosen(Long chatId, Update update) {
        System.out.println(chatId);
        TelegramBot bot = new TelegramBot(environment.getProperty("telegram.bot.token"));
        InlineQuery inlineQuery = update.inlineQuery();
        ChosenInlineResult chosenInlineResult = update.chosenInlineResult();
        System.out.println("res: " + chosenInlineResult);
        CallbackQuery callbackQuery = update.callbackQuery();
        System.out.println("callback: " + callbackQuery);
        InlineQueryResult r2 = new InlineQueryResultArticle(inlineQuery.id(), "title", "message text");
        BaseResponse response = bot.execute(new AnswerInlineQuery(inlineQuery.id(), r2));
        System.out.println(response.isOk() + " " + response.description());
        return null;
    }
}
