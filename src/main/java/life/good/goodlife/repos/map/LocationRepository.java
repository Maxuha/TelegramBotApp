package life.good.goodlife.repos.map;

import life.good.goodlife.model.map.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location getByUserId(Long userId);

    @Modifying
    @Query(value = "update user_location set latitude = ?1, longitude = ?2 where id = ?3;", nativeQuery = true)
    void updateLocation(Float lat, Float lng, Long id);
}
