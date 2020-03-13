package life.good.goodlife.component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotExecuteComponent {
    TelegramBot telegramBot;
    SendLocation sendLocation;
    private final Environment environment;

    public TelegramBotExecuteComponent(Environment environment) {
        this.environment = environment;
        telegramBot = new TelegramBot(environment.getProperty("telegram.bot.token"));
    }

    public void sendMessage(SendMessage sendMessage) {
        telegramBot.execute(sendMessage);
    }

    public void sendLocation(Long chatId, float lat, float lon) {
        sendLocation = new SendLocation(chatId, lat, lon);
        telegramBot.execute(sendLocation);
    }

    public void sendPhoto(SendPhoto sendPhoto) {
        telegramBot.execute(sendPhoto);
    }

    public GetFileResponse sendUploadStickerFile(UploadStickerFile uploadStickerFile) {
        return telegramBot.execute(uploadStickerFile);
    }

    public void sendSticker(SendSticker sendSticker) {
        telegramBot.execute(sendSticker);
    }
}
