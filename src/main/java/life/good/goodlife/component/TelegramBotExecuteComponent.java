package life.good.goodlife.component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotExecuteComponent {
    TelegramBot telegramBot;
    SendMessage sendMessage;
    SendLocation sendLocation;
    private final Environment environment;

    public TelegramBotExecuteComponent(Environment environment) {
        this.environment = environment;
        telegramBot = new TelegramBot(environment.getProperty("telegram.bot.token"));
    }

    public void sendMessage(Long chatId, String message) {
        sendMessage = new SendMessage(chatId, message);
        telegramBot.execute(sendMessage);
    }

    public void sendLocation(Long chatId, float lat, float lon) {
        sendLocation = new SendLocation(chatId, lat, lon);
        telegramBot.execute(sendLocation);
    }
}
