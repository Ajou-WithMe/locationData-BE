package ajou.withme.locationData.controller;

import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.dto.SaveLocationDto;
import ajou.withme.locationData.service.LocationService;
import ajou.withme.locationData.service.UserService;
import ajou.withme.locationData.util.ResFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;
    private final UserService userService;

    @PostMapping
    public ResFormat saveLocation(@RequestBody SaveLocationDto saveLocationDto) {

        User userByName = userService.findUserByName(saveLocationDto.getName());

        if (userByName == null) {
            return new ResFormat(false, 400L, "해당하는 user가 없습니다.");
        }

        Location location = locationService.saveLocation(saveLocationDto.toEntity(userByName));

        return new ResFormat(true, 201L, location);
    }
}
