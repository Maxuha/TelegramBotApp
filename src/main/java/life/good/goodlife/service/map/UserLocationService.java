package life.good.goodlife.service.map;

import life.good.goodlife.model.map.Location;

public interface UserLocationService {
    Location getUserLocationByUserId(Long userId);
}
