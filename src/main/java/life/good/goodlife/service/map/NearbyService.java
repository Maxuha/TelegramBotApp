package life.good.goodlife.service.map;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Location;
import life.good.goodlife.component.TelegramBotExecuteComponent;
import life.good.goodlife.model.map.GeoCodeMain;
import life.good.goodlife.model.map.NearbyMain;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class NearbyService {
    private String token;
    private final TelegramBotExecuteComponent telegramBotExecuteComponent;

    public NearbyService(TelegramBotExecuteComponent telegramBotExecuteComponent) {
        this.telegramBotExecuteComponent = telegramBotExecuteComponent;
        token = System.getenv().get("google_map_token");
    }

    public NearbyMain getNearbyPlaces(Location location, int radius) {
        String data = Request.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                location.latitude() + "," + location.longitude() + "&radius=" + radius + "&key=" + token + "&language=ru");
        Gson gson = new Gson();
        return gson.fromJson(data, NearbyMain.class);
    }
}
