package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.MainMenuComponent;
import life.good.goodlife.component.UserHistoryComponent;
import life.good.goodlife.model.bot.User;
import life.good.goodlife.model.bot.UserHistory;
import life.good.goodlife.service.bot.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@BotController
public class CreateUserController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);
    private final UserService userService;
    private final UserHistoryComponent userHistoryComponent;
    private final MainMenuComponent mainMenuComponent;

    public CreateUserController(UserService userService, UserHistoryComponent userHistoryComponent, MainMenuComponent mainMenuComponent) {
        this.userService = userService;
        this.userHistoryComponent = userHistoryComponent;
        this.mainMenuComponent = mainMenuComponent;
    }

    @BotRequest("/start")
    BaseRequest register(Long chatId) {
        User user = userService.findByChatId(chatId);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            user.setFullName("anonymous");
            user.setDate(LocalDateTime.now());
            userService.createUser(user);
        }
        userHistoryComponent.createUserHistory(user.getId(), "/start");

        String msg = "Привет! Выберете способ регистрации";

        SendMessage sendMessage = new SendMessage(chatId, msg);

        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Телеграм").requestContact(true),
                        new KeyboardButton("Быстрая регистрация"),
                        new KeyboardButton("Анонимность")
                }
        );

        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }

    @BotRequest("Телеграм")
    BaseRequest telegramRegister(Long chatId, Message message) {
        User userNew = userService.findByChatId(chatId);
        userHistoryComponent.createUserHistory(userNew.getId(), "Телеграм");
        userNew.setDate(LocalDateTime.now());
        userNew.setPhone(message.contact().phoneNumber());
        userNew.setFullName(message.contact().firstName() + " " + message.contact().lastName());
        userNew.setChatId(chatId);
        userService.createUser(userNew);
        return lastStepRegister(chatId);
    }

    @BotRequest("Анонимность")
    BaseRequest anonymousRegister(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryComponent.createUserHistory(user.getId(), "Анонимность");
        String msg = "Вы уверенны, что хотите быть анонимом?\n" +
                "В любом случае вы всегда сможете зарегистрироваться, посмотрев команды /help";
        SendMessage sendMessage = new SendMessage(chatId, msg);
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Продолжить"),
                        new KeyboardButton("Отмена")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }

    @BotRequest("Быстрая регистрация")
    BaseRequest quickRegister(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "Быстрая регистрация");
        String msg = "Введите команду /set_name и через пробел введите ваше имя. ";
        SendMessage sendMessage = new SendMessage(chatId, msg);
        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        return sendMessage;
    }

    @BotRequest(path = "/set_name **")
    BaseRequest setName(String text, Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/set_name");

        StringBuilder name = new StringBuilder();
        String[] results = text.split(" ");
        for (int i = 1; i < results.length; i++) {
            name.append(results[i]).append(" ");
        }
        String nameResult = name.toString().trim();

        User user = userService.findByChatId(chatId);
        user.setFullName(nameResult);
        userService.createUser(user);

        String msg = nameResult + ", введите команду /set_phone и через пробел введите ваш номер телефона.";
        msg += "\nИли нажмите на 'Предоставить номер'. Если не хотите предоставлять номер отправьте пустую команду /set_phone.";

        SendMessage sendMessage = new SendMessage(chatId, msg);

        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Предоставить номер").requestContact(true),
                        new KeyboardButton("Отмена")
                }
        );

        sendMessage.replyMarkup(replayKeyboard);

        return sendMessage;
    }

    @BotRequest("/set_phone **")
    BaseRequest setNumberPhone(Long chatId, String text) {
        User user = userService.findByChatId(chatId);
        user.setPhone(text.split(" ")[1]);
        userService.createUser(user);
        userHistoryComponent.createUserHistory(user.getId(), "/set_phone");
        String msg = "Введите команду /set_email и через пробел введите ваш email. Если не хотите предоставлять почту отправьте пустую команду /set_email.";
        SendMessage sendMessage = new SendMessage(chatId, msg);
        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        return sendMessage;
    }

    @BotRequest("/set_phone")
    BaseRequest setNumberPhoneEmpty(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryComponent.createUserHistory(user.getId(), "/set_phone");
        String msg = "Введите команду /set_email и через пробел введите ваш email. Если не хотите предоставлять почту отправьте пустую команду /set_email.";
        SendMessage sendMessage = new SendMessage(chatId, msg);
        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        return sendMessage;
    }

    @BotRequest("Отмена")
    BaseRequest cancel(Long chatId) {
        String msg = "";
        User user = userService.findByChatId(chatId);
        UserHistory userHistory = userHistoryComponent.findLastUserHistoryByUserId(user.getId());
        if (userHistory.getCommandsId() == 3) {
            msg = "Введите команду /set_email и через пробел введите ваш email. Если не хотите предоставлять почту отправьте пустую команду /set_email.";
        } else if (userHistory.getCommandsId() == 7) {
            return register(chatId);
        }
        userHistoryComponent.createUserHistory(user.getId(), "Отмена");
        SendMessage sendMessage = new SendMessage(chatId, msg);
        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        return sendMessage;
    }

    @BotRequest("Продолжить")
    BaseRequest next(Long chatId) {
        String msg = "";
        User user = userService.findByChatId(chatId);
        UserHistory userHistory = userHistoryComponent.findLastUserHistoryByUserId(user.getId());
        SendMessage sendMessage = new SendMessage(chatId, msg);
        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        if (userHistory.getCommandsId() == 7) {
            sendMessage = lastStepRegister(chatId);
        }
        userHistoryComponent.createUserHistory(user.getId(), "Продолжить");
        return sendMessage;
    }

    @BotRequest("/set_email **")
    BaseRequest setEmail(Long chatId, String text) {
        User user = userService.findByChatId(chatId);
        UserHistory userHistory = userHistoryComponent.findLastUserHistoryByUserId(user.getId());
        userHistoryComponent.createUserHistory(user.getId(), "/set_email");
        if (text.split(" ").length == 2) {
            user.setEmail(text.split(" ")[1]);
            userService.createUser(user);
        }
        if (userHistory.getCommandsId() == 4) {
            return lastStepRegister(chatId);
        } else {
            return mainMenuComponent.showMainMenu(chatId, "", null);
        }
    }

    @BotRequest("/set_email")
    BaseRequest setEmailEmpty(Long chatId) {
        User user = userService.findByChatId(chatId);
        UserHistory userHistory = userHistoryComponent.findLastUserHistoryByUserId(user.getId());
        userHistoryComponent.createUserHistory(user.getId(), "/set_email");
        if (userHistory.getCommandsId() == 4) {
            return lastStepRegister(chatId);
        } else {
            return mainMenuComponent.showMainMenu(chatId, "", null);
        }
    }

    SendMessage lastStepRegister(Long chatId) {
        return mainMenuComponent.showMainMenu(chatId, "Спасибо за регистрацию. Начнём.", null);
    }
}
