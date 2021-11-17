package ajou.withme.locationData.dto.visitOften.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MissingUserResonse {

    List<FindVisitOftenDto> visitOften;
    Double distance;
}
