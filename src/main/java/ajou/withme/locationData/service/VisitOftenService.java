package ajou.withme.locationData.service;

import ajou.withme.locationData.dto.LocationDto;
import ajou.withme.locationData.dto.visitOften.response.FindVisitOftenDto;
import ajou.withme.locationData.repository.VisitOftenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitOftenService {

    private final VisitOftenRepository visitOftenRepository;

    public List<FindVisitOftenDto> findVisitOftenDto(Long id) {
        List<Object[]> allVisitOftenByUserId = visitOftenRepository.findAllVisitOftenByUserId(id);

        List<FindVisitOftenDto> findVisitOftenDtos = new LinkedList<>();

        for (Object[] location:
                allVisitOftenByUserId) {
            FindVisitOftenDto locationDto = new FindVisitOftenDto((double)location[0], (double)location[1], (int)location[3]);
            findVisitOftenDtos.add(locationDto);
        }

        return findVisitOftenDtos;
    }
}
