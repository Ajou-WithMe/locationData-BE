package ajou.withme.locationData.dto.request;

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

    // userName -> 나중에 토큰쓰고 uid로 바꿔야함.
//    private String name;

    public Location toEntity(User user) {
        return Location.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .user(user)
                .build();
    }
}
