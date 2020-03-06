package life.good.goodlife.component;

import life.good.goodlife.model.map.Location;
import life.good.goodlife.model.map.LocationType;
import life.good.goodlife.repos.map.LocationRepository;
import org.springframework.stereotype.Component;

@Component
public class LocationComponent {
    private final LocationRepository locationRepository;

    public LocationComponent(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void createNewLocation(Long userId, float lat, float lng, LocationType type) {
        Location locationBD = new Location();
        locationBD.setLat(lat);
        locationBD.setLng(lng);
        locationBD.setUserId(userId);
        locationBD.setType(type.toString());
        locationRepository.save(locationBD);
    }
}
