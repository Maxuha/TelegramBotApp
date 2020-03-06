package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
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

    @BotRequest("@my_good_life_bot **")
    BaseRequest testInlineQuery(Long chatId) {
        TelegramBot bot = new TelegramBot(environment.getProperty("telegram.bot.token"));
        GetUpdatesResponse updatesResponse = bot.execute(new GetUpdates());
        List<Update> updates = updatesResponse.updates();
        for (Update update : updates) {
            InlineQuery inlineQuery = update.inlineQuery();
            ChosenInlineResult chosenInlineResult = update.chosenInlineResult();
            CallbackQuery callbackQuery = update.callbackQuery();
            InlineQueryResult r2 = new InlineQueryResultArticle(inlineQuery.id(), "title", "message text").thumbUrl("url");
            BaseResponse response = bot.execute(new AnswerInlineQuery(inlineQuery.id(), r2));
            System.out.println(response.isOk() + " " + response.description());
        }
        return null;
    }
}
