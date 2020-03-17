package life.good.goodlife.component;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.service.bot.CommandService;
import org.springframework.stereotype.Component;

@Component
public class MonoBankComponent {
    private final CommandService commandService;

    public MonoBankComponent(CommandService commandService) {
        this.commandService = commandService;
    }

    public SendMessage showMonoBankMenu(Long chatId) {
        String msg = commandService.findCommandsByName("Банкинг").getFullDescription();
        SendMessage sendMessage = new SendMessage(chatId, msg);
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Мой баланс"),
                        new KeyboardButton("Курс валют"),
                        new KeyboardButton("Выписка"),
                        new KeyboardButton("Контроль расходами"),
                        new KeyboardButton("Синхроннизация выписки"),
                        new KeyboardButton("Главное меню")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }
}
