package ajou.withme.locationData.dao;

import lombok.Data;

@Data
public class LocationRedis {
    // 위도
    private final Double latitude;
    // 경도
    private final Double longitude;
}
