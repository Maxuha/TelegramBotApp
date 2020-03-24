package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.statics.Request;

import java.util.HashMap;
import java.util.Map;

@BotController
public class MusicController {
    @BotRequest("Музыка")
    BaseRequest music(Long chatId) {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", System.getenv().get("music_api_key"));
        String response = Request.doGet("http://www.last.fm/api/auth/", params, null);
        return new SendMessage(chatId, response);
    }
}
