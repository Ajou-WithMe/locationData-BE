package ajou.withme.locationData.service;

import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.domain.ZoneLocation;
import ajou.withme.locationData.dto.LocationDto;
import ajou.withme.locationData.repository.ZoneLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneLocationService {
    private final ZoneLocationRepository zoneLocationRepository;

    public List<ZoneLocation> findZoneLocationByUser(User userByUid) {
        return zoneLocationRepository.findAllByUser(userByUid);
    }

    public List<Object[]> findAllLocationByUserId(Long id) {
        return zoneLocationRepository.findAllLocationByUserId(id);
    }
}
