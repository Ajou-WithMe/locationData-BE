package ajou.withme.locationData.domain;

import ajou.withme.locationData.dao.LocationRedis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RedisHash("user")
public class UserRedis {

    @Id
    private String id;

    private Double distance;
    private Double time;
    private LocationRedis curLocation;
    private List<LocationRedis> locations;
}
