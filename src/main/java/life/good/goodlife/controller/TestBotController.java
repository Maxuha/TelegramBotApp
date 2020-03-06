package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.github.telegram.mvc.api.MessageType;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

@BotController
public class TestBotController {
    @BotRequest(messageType = MessageType.INLINE_QUERY)
    BaseRequest testInlineQuery(Long chatId, Update update) {
        System.out.println(chatId);
        System.out.println(update);
        InlineQuery inlineQuery = update.inlineQuery();
        InlineQueryResult[] results = new InlineQueryResult[1];
        //command sp - search_places
        results[0] = new InlineQueryResultArticle(inlineQuery.id(), "", "/search_places");
        if (update.inlineQuery().query().equals("search_places")) {
            return new SendMessage(chatId, "Введите, что ищете?");
        } else {
            return new AnswerInlineQuery(inlineQuery.id(), results[0]);
        }
    }
}
