package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, String > {
}
