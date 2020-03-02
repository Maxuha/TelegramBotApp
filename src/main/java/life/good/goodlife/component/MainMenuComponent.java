package life.good.goodlife.component;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class MainMenuComponent {
    public SendMessage showMainMenu(Long id, String addMessage) {
        SendMessage sendMessage = new SendMessage(id, addMessage + "\n\nВыберете, что будем делать дальше.");
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Банкинг"),
                        new KeyboardButton("Погода"),
                        new KeyboardButton("Новости")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }
}
