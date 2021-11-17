package ajou.withme.locationData.dto.visitOften.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindVisitOftenDto {
    Double longitude;
    Double latitude;
    int grade;
}
