package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository  extends JpaRepository<Location, Long> {
}
