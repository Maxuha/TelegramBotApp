package life.good.goodlife.service.map;

import com.pengrad.telegrambot.model.Location;
import life.good.goodlife.model.map.NearbyMain;

public interface NearbyService {
    NearbyMain getNearbyPlaces(Location location, int radius, Long userId);
}
