package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

@BotController
public class TestController {

    @BotRequest("/test **")
    BaseRequest test(Long chatId, String message) {
        return new SendMessage(chatId, message.split(" ")[1]);
    }
}
