package life.good.goodlife.component;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MainMenuComponent {
    private static Map<String, String> currentData;

    public SendMessage showMainMenu(Long id, String addMessage, Map<String, String> data) {
        SendMessage sendMessage = new SendMessage(id, addMessage + "\n\nВыберете, что будем делать дальше.");
        if (data != null) {
            currentData = data;
        }

        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Банкинг + \n Баланс: " + currentData.get("Monobank")),
                        new KeyboardButton("Погода"),
                        new KeyboardButton("Новости")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }
}
