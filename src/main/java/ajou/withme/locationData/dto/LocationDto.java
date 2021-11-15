package ajou.withme.locationData.dto;

import ajou.withme.locationData.domain.ZoneLocation;
import lombok.Data;

@Data
public class LocationDto {
    Double longitude;
    Double latitude;

    public LocationDto(ZoneLocation zoneLocation) {
        this.latitude = zoneLocation.getLatitude();
        this.longitude = zoneLocation.getLongitude();
    }

    public LocationDto(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
