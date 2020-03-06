package life.good.goodlife.repos.map;

import life.good.goodlife.model.map.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location getByUserId(Long userId);
}
