package ajou.withme.locationData.dao;

import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
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

    public Location toEntity(User user) {
        return Location.builder()
                .user(user)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .createdAt(this.createdAt)
                .build();
    }
}
