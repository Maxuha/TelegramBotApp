package life.good.goodlife.service.map;

import life.good.goodlife.model.map.Location;
import life.good.goodlife.repos.map.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class UserLocationServiceImpl implements UserLocationService {
    private final LocationRepository locationRepository;

    public UserLocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getUserLocationByUserId(Long userId) {
        return locationRepository.getByUserId(userId);
    }
}
