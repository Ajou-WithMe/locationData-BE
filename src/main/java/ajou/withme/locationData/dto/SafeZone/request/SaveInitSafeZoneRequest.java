package ajou.withme.locationData.dto.SafeZone.request;

import lombok.Data;

import java.util.List;

@Data
public class SaveInitSafeZoneRequest {

    @Data
    public static class LatLng{
        Double latitude;
        Double longitude;
    }

    List<LatLng> safeZone;
}
