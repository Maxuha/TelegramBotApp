package life.good.goodlife.component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotExecuteComponent {
    TelegramBot telegramBot;
    SendMessage sendMessage;
    private final Environment environment;

    public TelegramBotExecuteComponent(Environment environment) {
        this.environment = environment;
        telegramBot = new TelegramBot(environment.getProperty("telegram.bot.token"));
    }

    public void sendMessage(Long chatId, String message) {
        sendMessage = new SendMessage(chatId, message);
        telegramBot.execute(sendMessage);
    }
}
