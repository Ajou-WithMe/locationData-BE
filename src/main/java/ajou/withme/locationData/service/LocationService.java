package ajou.withme.locationData.service;

import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public void saveAll(List<Location> locationEntity) {
        locationRepository.saveAll(locationEntity);
    }

    public List<Location> findAllLocationByUser(User user) {
        return locationRepository.findAllByUser(user);
    }
}
