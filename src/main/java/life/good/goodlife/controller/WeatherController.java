package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.MainMenuComponent;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.component.UserHistoryComponent;
import life.good.goodlife.service.bot.CommandService;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.weather.WeatherService;
import org.springframework.core.env.Environment;

@BotController
public class WeatherController {
    private final WeatherService weatherService;
    private final UserHistoryComponent userHistoryComponent;
    private final UserService userService;
    private final CommandService commandService;
    private final Environment environment;
    private final MainMenuComponent mainMenuComponent;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;

    public WeatherController(WeatherService weatherService, UserHistoryComponent userHistoryComponent, UserService userService, CommandService commandService, Environment environment, MainMenuComponent mainMenuComponent, TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.weatherService = weatherService;
        this.userHistoryComponent = userHistoryComponent;
        this.userService = userService;
        this.commandService = commandService;
        this.environment = environment;
        this.mainMenuComponent = mainMenuComponent;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }

    @BotRequest("Погода")
    BaseRequest weather(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "Погода");
        String msg = commandService.findCommandsByName("Погода").getFullDescription();
        SendMessage sendMessage = new SendMessage(chatId, msg);
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Предоставить местоположение").requestLocation(true),
                        new KeyboardButton("Главное меню")
                }
        );
        sendMessage.replyMarkup(replayKeyboard);
        return sendMessage;
    }

    @BotRequest("/weather **")
    BaseRequest weatherByCity(Long chatId, String text) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/weather");
        String[] results = text.split(" ");
        StringBuilder city = new StringBuilder();
        String response = "";
        if (results.length >= 2) {
            for (int i = 1; i < results.length; i++) {
                city.append(results[i]).append(" ");
            }
            response = weatherService.weatherByCity(city.toString().trim());
        } else {
            response = "Укажите город или предоставьте своё местопложение.";
        }
        return mainMenuComponent.showMainMenu(chatId, response, null);
    }

    @BotRequest("/weather6 **")
    BaseRequest weatherFiveByCity(Long chatId, String text) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/weather6");
        String[] results = text.split(" ");
        StringBuilder city = new StringBuilder();
        String[] data;
        String response = "";
        if (results.length >= 2) {
            for (int i = 1; i < results.length; i++) {
                city.append(results[i]).append(" ");
            }
            data = weatherService.weatherFiveByCity(city.toString().trim());
            for (int i = 0; i < data.length; i++) {
                telegramBotExecuteComponent.sendMessage(chatId, data[i]);
            }
        } else {
            response = "Укажите город или предоставьте своё местопложение.";
        }
        return mainMenuComponent.showMainMenu(chatId, response, null).disableNotification(false);
    }
}
