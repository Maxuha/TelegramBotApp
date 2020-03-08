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
import life.good.goodlife.service.bot.*;
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

    public WeatherController(WeatherService weatherService, UserHistoryService userHistoryService, UserService userService,
                             CommandService commandService, MainMenuComponent mainMenuComponent,
                             TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.weatherService = weatherService;
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.commandService = commandService;
        this.mainMenuComponent = mainMenuComponent;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
    }

    @BotRequest("Погода")
    BaseRequest weather(Long chatId) {
        logger.info("Find command: 'Погода'");
        Command command = commandService.findCommandsByName("Погода");
        logger.info("Creating history command 'Погода'");
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), command);
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
        logger.info("Find command: '/weather'");
        Command command = commandService.findCommandsByName("/weather");
        logger.info("Creating history command '/weather'");
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), command);
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
        logger.info("Find command: '/weather6'");
        Command command = commandService.findCommandsByName("/weather6");
        logger.info("Creating history command '/weather6'");
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), command);
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
