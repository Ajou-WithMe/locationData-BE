package ajou.withme.locationData.controller;

import ajou.withme.locationData.dao.LocationRedis;
import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.domain.UserRedis;
import ajou.withme.locationData.dto.SaveLocationDto;
import ajou.withme.locationData.repository.LocationJdbcRepository;
import ajou.withme.locationData.service.LocationService;
import ajou.withme.locationData.service.UserRedisService;
import ajou.withme.locationData.service.UserService;
import ajou.withme.locationData.util.ResFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
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
    private final LocationJdbcRepository locationJdbcRepository;

    @PostMapping
    public ResFormat saveLocation(@RequestBody SaveLocationDto saveLocationDto) {

        User userByName = userService.findUserByName(saveLocationDto.getName());

        if (userByName == null) {
            return new ResFormat(false, 400L, "해당하는 user가 없습니다.");
        }

        // 레디스 : userName을 키로 현재위치 바꾸고, locations 리스트로 계속 담고, 속도도 5초 속도로 계산해서 거리, 시간 담고
        UserRedis userRedis = userRedisService.findUserRedisById(saveLocationDto.getName());

        Double distance = saveLocationDto.getSpeed() * 3.6 * 5;

        LocationRedis location = new LocationRedis(saveLocationDto.getLatitude(), saveLocationDto.getLongitude(), new Date());

        if (userRedis == null) {
            List<LocationRedis> locationRedisList = new LinkedList<>();
            locationRedisList.add(location);

            userRedis = UserRedis.builder()
                    .id(saveLocationDto.getName())
                    .curLocation(location)
                    .locations(locationRedisList)
                    .distance(distance)
                    .time(5L)
                    .build();
            log.error("------레디스캐시 비어있음------");

        } else {
            // 현재 위치랑 차이가 크지 않으면 버림. 레디스에 안넣음.
            userRedis.updateUserRedis(distance, location);
        }

        // 만약 리스트 숫자가 일정이상(ex) 36개 3분)이 되면 배치작업으로 다 넣어버림. -> user에 거리, 시간. location
        if (userRedis.getLocations().size() >= 36) {
            // user update
            userByName.addTime(userRedis.getTime());
            userByName.addDistance(userRedis.getDistance());
            userService.saveUser(userByName);

            // batch location 넣기
            List<LocationRedis> locations = userRedis.getLocations();
            List<Location> locationEntity = new LinkedList<>();

            for (LocationRedis curLocationRedis : locations
            ) {
                Location curLocation = curLocationRedis.toEntity(userByName);
                locationEntity.add(curLocation);
            }

            locationJdbcRepository.saveAll(locationEntity);

            // userRedis 초기화
            userRedis.resetLocations();
            userRedis.resetDistance();
            userRedis.resetTime();

            log.error("------batch 작업------");
        }

        userRedisService.saveUserRedis(userRedis);
        log.error("------레디스캐시 세이브------");

//        Location savedlocation = locationService.saveLocation(saveLocationDto.toEntity(userByName));

        return new ResFormat(true, 201L, userRedis.getCurLocation());
    }


    @GetMapping("/redis")
    public ResFormat getUserRedis(@RequestParam String name) {
        UserRedis userRedis = userRedisService.findUserRedisById(name);

        return new ResFormat(true, 200L, userRedis);
    }

}
