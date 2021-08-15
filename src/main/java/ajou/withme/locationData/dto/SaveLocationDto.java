package ajou.withme.locationData.dto;

import ajou.withme.locationData.domain.Location;
import lombok.Data;

@Data
public class SaveLocationDto {
    // 위도
    private Double latitude;

    // 경도
    private Double longitude;

    // userName
    private String name;

    public Location toEntity() {
        return Location.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .name(this.name)
                .build();
    }
}
