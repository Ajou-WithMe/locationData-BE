package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.VisitOften;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitOftenRepository extends JpaRepository<VisitOften, Long> {

    @Query(value = "select longitude, latitude, grade from visit_often where user_id = ?1", nativeQuery = true)
    List<Object[]> findAllVisitOftenByUserId(Long id);

}
