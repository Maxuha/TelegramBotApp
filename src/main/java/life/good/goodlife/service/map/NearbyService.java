package life.good.goodlife.service.map;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Location;
import life.good.goodlife.component.LocationComponent;
import life.good.goodlife.model.map.LocationType;
import life.good.goodlife.model.map.NearbyMain;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

@Service
public class NearbyService {
    private final LocationComponent locationComponent;
    private String token;

    public NearbyService(LocationComponent locationComponent) {
        this.locationComponent = locationComponent;
        token = System.getenv().get("google_map_token");
    }

    public NearbyMain getNearbyPlaces(Location location, int radius, Long userId) {
        locationComponent.createNewLocation(userId, location.latitude(), location.longitude(), LocationType.CURRENT);
        String data = Request.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                location.latitude() + "," + location.longitude() + "&radius=" + radius + "&key=" + token + "&language=ru");
        Gson gson = new Gson();
        return gson.fromJson(data, NearbyMain.class);
    }
}
