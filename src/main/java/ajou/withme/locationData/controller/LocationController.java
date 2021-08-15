package ajou.withme.locationData.controller;

import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.dto.SaveLocationDto;
import ajou.withme.locationData.service.LocationService;
import ajou.withme.locationData.util.ResFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResFormat saveLocation(@RequestBody SaveLocationDto saveLocationDto) {

        Location location = locationService.saveLocation(saveLocationDto.toEntity());

        return new ResFormat(true, 201L, location);
    }
}
