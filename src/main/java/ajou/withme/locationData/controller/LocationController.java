package ajou.withme.locationData.controller;

import ajou.withme.locationData.dao.LocationRedis;
import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.domain.UserRedis;
import ajou.withme.locationData.dto.SaveLocationDto;
import ajou.withme.locationData.service.LocationService;
import ajou.withme.locationData.service.UserRedisService;
import ajou.withme.locationData.service.UserService;
import ajou.withme.locationData.util.ResFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;
    private final UserService userService;
    private final UserRedisService userRedisService;

    @PostMapping
    public ResFormat saveLocation(@RequestBody SaveLocationDto saveLocationDto) {

        User userByName = userService.findUserByName(saveLocationDto.getName());

        if (userByName == null) {
            return new ResFormat(false, 400L, "해당하는 user가 없습니다.");
        }

        // 레디스 : userName을 키로 현재위치 바꾸고, locations 리스트로 계속 담고, 속도도 5초 속도로 계산해서 거리, 시간 담고
        // 현재 위치랑 차이가 크지 않으면 버림. 레디스에 안넣음.
        // 만약 리스트 숫자가 일정이상(ex) 36개 3분)이 되면 배치작업으로 다 넣어버림.
        Location location = locationService.saveLocation(saveLocationDto.toEntity(userByName));

        return new ResFormat(true, 201L, location);
    }

    @PostMapping("/test")
    public ResFormat test() {

        List<LocationRedis> locations = new LinkedList<>();

        locations.add(new LocationRedis(15.213, 133.234));
        locations.add(new LocationRedis(1111.213, 133.234));

        UserRedis userRedis = UserRedis.builder()
                .curLocation(new LocationRedis(15.213, 111.234))
                .id("taek")
                .distance(14.42)
                .time(12D)
                .locations(locations)
                .build();

        UserRedis userRedis1 = userRedisService.saveUserRedis(userRedis);

        return new ResFormat(true, 201L, userRedis1);
    }
}
