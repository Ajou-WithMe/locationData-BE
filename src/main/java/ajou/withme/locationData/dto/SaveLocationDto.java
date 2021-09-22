package ajou.withme.locationData.dto;

import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
import lombok.Data;

@Data
public class SaveLocationDto {
    // 위도
    private Double latitude;

    // 경도
    private Double longitude;

    private Double speed;

    // userName
    private String name;

    public Location toEntity(User user) {
        return Location.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .user(user)
                .build();
    }
}
