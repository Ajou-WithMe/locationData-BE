package ajou.withme.locationData.domain;

import ajou.withme.locationData.dao.LocationRedis;
import ajou.withme.locationData.dto.SaveLocationDto;
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
    private Long time;
    private Location curLocation;
    private List<Location> locations;

    public void updateUserRedis(Double distance, Location location) {

        double latitudeAbs = Math.abs(this.curLocation.getLatitude() - location.getLatitude());
        double longitudeAbs = Math.abs(this.curLocation.getLongitude() - location.getLongitude());
        double locationDistance = Math.sqrt(Math.pow(latitudeAbs, 2) + Math.pow(longitudeAbs, 2));

        // 약 10미터 이상 차이가 나면 업데이트
        if (0.0001 < locationDistance) {
            return;
        }

        this.distance += distance;
        this.time += 5;
        this.curLocation = location;
        locations.add(location);

    }

    public void resetLocations() {
        locations.clear();
    }

    public void resetDistance() {
        this.distance = 0D;
    }

    public void resetTime() {
        this.time = 0L;
    }
}
