package ajou.withme.locationData.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class GetAllLocatoinDto {
    private final Double latitude;
    private final Double longitude;
    private final Date date;
    private final String name;
}
