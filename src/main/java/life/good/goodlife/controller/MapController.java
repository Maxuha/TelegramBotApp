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
import life.good.goodlife.component.UserHistoryComponent;
import life.good.goodlife.model.map.GeoCodeMain;
import life.good.goodlife.model.map.Location;
import life.good.goodlife.service.bot.UserService;
import life.good.goodlife.service.map.GeoCodeService;

@BotController
public class MapController {
    private final GeoCodeService geoCodeService;
    private final UserHistoryComponent userHistoryComponent;
    private final UserService userService;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;
    private final MainMenuComponent mainMenuComponent;

    public MapController(GeoCodeService geoCodeService, UserHistoryComponent userHistoryComponent, UserService userService, TelegramBotExecuteComponent telegramBotExecuteComponent, MainMenuComponent mainMenuComponent) {
        this.geoCodeService = geoCodeService;
        this.userHistoryComponent = userHistoryComponent;
        this.userService = userService;
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        this.mainMenuComponent = mainMenuComponent;
    }

    @BotRequest("Карта")
    BaseRequest mapBtn(Long chatId) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/map");
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Поиск места").requestLocation(true),
                        new KeyboardButton("Что поблизости?").requestLocation(true),
                        new KeyboardButton("Главное меню")
                }
        );
        return mainMenuComponent.showMainMenu(chatId, "", null);
    }

    @BotRequest("/search_places **")
    BaseRequest getSearchPlace(Long chatId, String text) {
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/search_places");
        StringBuilder place = new StringBuilder();
        String[] partPlace = text.split(" ");
        for (int i = 1; i < partPlace.length; i++) {
            place.append(partPlace[i]).append(" ");
        }

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
        userHistoryComponent.createUserHistory(userService.findByChatId(chatId).getId(), "/nearby");
        Keyboard replayKeyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] {
                        new KeyboardButton("Предоставить местоположение").requestLocation(true),
                        new KeyboardButton("Главное меню")
                }
        );
        return new SendMessage(chatId, "Предоставьте свое местоположение").replyMarkup(replayKeyboard);
    }
}
