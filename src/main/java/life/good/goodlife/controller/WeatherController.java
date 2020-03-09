package life.good.goodlife.controller;

import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import life.good.goodlife.component.MainMenuComponent;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.bot.Command;
import life.good.goodlife.model.bot.User;
import life.good.goodlife.model.map.Location;
import life.good.goodlife.service.bot.*;
import life.good.goodlife.service.map.UserLocationService;
import life.good.goodlife.service.weather.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@BotController
public class WeatherController {
    private static Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;
    private final UserHistoryService userHistoryService;
    private final UserService userService;
    private final CommandService commandService;
    private final MainMenuComponent mainMenuComponent;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final UserLocationService userLocationService;

    public WeatherController(WeatherService weatherService, UserHistoryService userHistoryService, UserService userService,
                             CommandService commandService, MainMenuComponent mainMenuComponent,
                             TelegramBotExecuteComponent telegramBotExecuteComponent, UserLocationService userLocationService) {
        this.weatherService = weatherService;
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.commandService = commandService;
        this.mainMenuComponent = mainMenuComponent;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.userLocationService = userLocationService;
    }

    @BotRequest("Погода")
    BaseRequest weather(Long chatId) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "Погода", "");
        String msg = commandService.findCommandsByName("Погода").getFullDescription();
        Location location = userLocationService.getUserLocationByUserId(user.getId());
        if (location != null) {
            logger.info("Get weather");
            msg += "\n" + weatherService.weather(location.getLat(), location.getLng(), user.getId());
        }
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Предоставить местоположение").requestLocation(true),
                        new KeyboardButton("Главное меню")
                }
        );
        return new SendMessage(chatId, msg).replyMarkup(replayKeyboard);
    }

    @BotRequest("/weather **")
    BaseRequest weatherByCity(Long chatId, String text) {
        User user = userService.findByChatId(chatId);
        userHistoryService.createUserHistory(user.getId(), "/weather", "");
        String[] results = text.split(" ");
        StringBuilder city = new StringBuilder();
        String response = "";
        if (results.length >= 2) {
            for (int i = 1; i < results.length; i++) {
                city.append(results[i]).append(" ");
            }
            logger.info("Get weather by {}", city.toString().trim());
            response = weatherService.weatherByCity(city.toString().trim());
        } else {
            response = "Укажите город или предоставьте своё местопложение.";
        }
        return mainMenuComponent.showMainMenu(chatId, response, null);
    }

    @BotRequest("/weather6 **")
    BaseRequest weatherFiveByCity(Long chatId, String text) {
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), "/weather6", "");
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
