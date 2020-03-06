package life.good.goodlife.repos.map;

import life.good.goodlife.model.map.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location getByUserId(Long userId);

    @Query(value = "UPDATE user_location SET latitude = ?1, longitude = ?2 WHERE id = ?3;", nativeQuery = true)
    void updateLocation(float lat, float lng, Long id);
}
