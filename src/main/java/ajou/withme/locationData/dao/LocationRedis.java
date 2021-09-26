package ajou.withme.locationData.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class LocationRedis {
    // 위도
    private Double latitude;
    // 경도
    private Double longitude;

    private Date createdAt;

}
