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
import life.good.goodlife.model.map.GeoCodeMain;
import life.good.goodlife.model.map.Location;
import life.good.goodlife.service.bot.CommandService;
import life.good.goodlife.service.bot.UserHistoryService;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.map.GeoCodeService;
import life.good.goodlife.service.map.UserLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@BotController
public class MapController {
    private static Logger logger = LoggerFactory.getLogger(MapController.class);
    private final GeoCodeService geoCodeService;
    private final UserHistoryService userHistoryService;
    private final UserService userService;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final MainMenuComponent mainMenuComponent;
    private final CommandService commandService;
    private final UserLocationService userLocationService;

    public MapController(GeoCodeService geoCodeService, UserHistoryService userHistoryService, UserService userService,
                         TelegramBotExecuteComponent telegramBotExecuteComponent, MainMenuComponent mainMenuComponent,
                         CommandService commandService, UserLocationService userLocationService) {
        this.geoCodeService = geoCodeService;
        this.userHistoryService = userHistoryService;
        this.userService = userService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.mainMenuComponent = mainMenuComponent;
        this.commandService = commandService;
        this.userLocationService = userLocationService;
    }

    @BotRequest("Карта")
    BaseRequest mapBtn(Long chatId) {
        logger.info("Find command: 'Карта'");
        Command command = commandService.findCommandsByName("Карта");
        User user = userService.findByChatId(chatId);
        logger.info("Creating history command 'Карта'");
        userHistoryService.createUserHistory(user.getId(), command);
        String msg = commandService.findCommandsByName("Карта").getFullDescription();
        Location location = userLocationService.getUserLocationByUserId(user.getId());
        KeyboardButton searchPlace;
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Поиск места"),
                        new KeyboardButton("Что поблизости?"),
                        new KeyboardButton("Главное меню")
                }
        );

        return new SendMessage(chatId, msg).replyMarkup(replayKeyboard);
    }

    @BotRequest("/search_places **")
    BaseRequest getSearchPlace(Long chatId, String text) {
        logger.info("Find command: '/search_places'");
        Command command = commandService.findCommandsByName("/search_places");
        logger.info("Creating history command '/search_places'");
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), command);
        StringBuilder place = new StringBuilder();
        String[] partPlace = text.split(" ");
        for (int i = 1; i < partPlace.length; i++) {
            place.append(partPlace[i]).append(" ");
        }

        logger.info("Get geo code");
        GeoCodeMain geoCodeMain = geoCodeService.getInfoPlace(place.toString());

        String[] data = geoCodeMain.toString().split("::");

        Location location;

        for (int i = 0; i < geoCodeMain.getResults().length; i++) {
            telegramBotExecuteComponent.sendMessage(chatId, data[i]);
            location = geoCodeMain.getResults()[i].getGeometry().getLocation();
            telegramBotExecuteComponent.sendLocation(chatId, location.getLat(), location.getLng());
        }

        return mainMenuComponent.showMainMenu(chatId, "", null);
    }

    @BotRequest("/nearby")
    BaseRequest getNearbyPlace(Long chatId) {
        logger.info("Find command: '/nearby'");
        Command command = commandService.findCommandsByName("/nearby");
        logger.info("Creating history command '/nearby'");
        userHistoryService.createUserHistory(userService.findByChatId(chatId).getId(), command);
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Предоставить местоположение").requestLocation(true),
                        new KeyboardButton("Главное меню")
                }
        );
        return new SendMessage(chatId, "Предоставьте свое местоположение").replyMarkup(replayKeyboard);
    }
}
