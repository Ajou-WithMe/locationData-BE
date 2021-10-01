package ajou.withme.locationData.domain;

import ajou.withme.locationData.dao.LocationRedis;
import ajou.withme.locationData.dto.SaveLocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RedisHash("user")
@Slf4j
public class UserRedis {

    @Id
    private String id;

    private Double distance;
    private Long time;
    private LocationRedis curLocation;
    private List<LocationRedis> locations;

    public void updateUserRedis(Double distance, LocationRedis location, Long time) {

        double latitudeAbs = Math.abs(this.curLocation.getLatitude() - location.getLatitude());
        double longitudeAbs = Math.abs(this.curLocation.getLongitude() - location.getLongitude());
        double locationDistance = Math.sqrt(Math.pow(latitudeAbs, 2) + Math.pow(longitudeAbs, 2));

        if (locations == null) {
            locations = new LinkedList<>();
        }

        // 약 10미터 이상 차이가 나면 업데이트
        if (0.0001 > locationDistance) {
            log.error("------직전위치랑 차이없음------");
            return;
        }
        if (1.38 > distance){
            log.error("------속도가 1미만------");
            return;
        }

        this.distance += distance;
        this.time += time;
        this.curLocation = location;
        locations.add(location);
        log.error("------위치업데이트------");

    }

    public void resetLocations() {
        locations = new LinkedList<>();
    }

    public void resetDistance() {
        this.distance = 0D;
    }

    public void resetTime() {
        this.time = 0L;
    }
}
