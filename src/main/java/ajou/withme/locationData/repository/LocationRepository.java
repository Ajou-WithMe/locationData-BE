package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository  extends JpaRepository<Location, Long> {
    List<Location> findAllByUser(User user);
}
