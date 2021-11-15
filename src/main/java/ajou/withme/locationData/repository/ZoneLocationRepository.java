package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.domain.ZoneLocation;
import ajou.withme.locationData.dto.LocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZoneLocationRepository extends JpaRepository<ZoneLocation, Long> {
    List<ZoneLocation> findAllByUser(User userByUid);

    @Query(value = "select longitude, latitude from zone_location where user_id = ?1", nativeQuery = true)
    List<Object[]> findAllLocationByUserId(Long id);
}
