package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.Person;
import ajou.withme.locationData.domain.UserRedis;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<UserRedis, String > {
}
