package ajou.withme.locationData.controller;

import ajou.withme.locationData.dao.LocationRedis;
import ajou.withme.locationData.domain.Location;
import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.domain.UserOption;
import ajou.withme.locationData.domain.UserRedis;
import ajou.withme.locationData.dto.request.SaveLocationDto;
import ajou.withme.locationData.dto.response.GetAllLocatoinDto;
import ajou.withme.locationData.repository.LocationJdbcRepository;
import ajou.withme.locationData.service.LocationService;
import ajou.withme.locationData.service.UserOptionService;
import ajou.withme.locationData.service.UserRedisService;
import ajou.withme.locationData.service.UserService;
import ajou.withme.locationData.util.CalculateDistance;
import ajou.withme.locationData.util.JwtTokenUtil;
import ajou.withme.locationData.util.ResFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    @Value("${batchSize}")
    private int batchSize;

    private final UserService userService;
    private final UserRedisService userRedisService;
    private final LocationJdbcRepository locationJdbcRepository;
    private final LocationService locationService;
    private final UserOptionService userOptionService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResFormat saveLocation(HttpServletRequest request, @RequestBody SaveLocationDto saveLocationDto) {

        String uid = jwtTokenUtil.getSubject(request);
        User userByName = userService.findUserByUid(uid);
        System.out.println(userByName.getName());
        UserOption userOptionByUser = userOptionService.findUserOptionByUser(userByName);
        System.out.println("userOptionByUser.getUser().getName() = " + userOptionByUser.getUser().getName());
        if (userByName == null) {
            return new ResFormat(false, 400L, "해당하는 user가 없습니다.");
        }

        // 레디스 : uid을 키로 현재위치 바꾸고, locations 리스트로 계속 담고, 속도도 5초 속도로 계산해서 거리, 시간 담고
        UserRedis userRedis = userRedisService.findUserRedisById(uid);

//       current network time update
        userOptionByUser.updateCurrentNetwork();

        Double distance = saveLocationDto.getSpeed() / 3.6 * 5;
        Long time = 5L;

        LocationRedis location = new LocationRedis(saveLocationDto.getLatitude(), saveLocationDto.getLongitude(), new Date());

        if (userRedis == null) {
            List<LocationRedis> locationRedisList = new LinkedList<>();
            locationRedisList.add(location);

            userRedis = UserRedis.builder()
                    .id(uid)
                    .curLocation(location)
                    .locations(locationRedisList)
                    .distance(distance)
                    .time(5L)
                    .build();
            log.error("------레디스캐시 비어있음------");

        } else {
            // 거리 계산. 직전 위치 시간과 10초 이상 차이가 안나면, 거리로 계산. 10초 이상 차이나면, 받아온 속도로 계산
            if (userRedis.getCurLocation().getCreatedAt().after(new Date(System.currentTimeMillis()-10000)) && saveLocationDto.getSpeed() > 5.0D) {
//            10초 차이가 안남.
                log.error("------직전위치 10초 이상 차이 안남 && 스피드 5보다 빠름------");
                distance = CalculateDistance.distance(userRedis.getCurLocation().getLatitude(), userRedis.getCurLocation().getLongitude(), saveLocationDto.getLatitude(), saveLocationDto.getLongitude(), "meter");
                time = (new Date().getTime() - userRedis.getCurLocation().getCreatedAt().getTime()) / 1000;
                log.error("------new distance------ : "+ distance);

            }

            // 현재 위치랑 차이가 크지 않으면 버림. 레디스에 안넣음.
            userRedis.updateUserRedis(distance, location, time);
        }

        // 만약 리스트 숫자가 일정이상(ex) 36개 3분)이 되면 배치작업으로 다 넣어버림. -> user에 거리, 시간. location
        if (userRedis.getLocations().size() >= batchSize) {
            // user update
            userOptionByUser.addTime(userRedis.getTime());
            userOptionByUser.addDistance(userRedis.getDistance());

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


        userOptionService.saveUser(userOptionByUser);

        userRedisService.saveUserRedis(userRedis);
        log.error("------레디스캐시 세이브------");

        return new ResFormat(true, 201L, userRedis.getCurLocation());
    }


    @GetMapping("/current")
    public ResFormat getUserCurrentLocation(HttpServletRequest request, @RequestParam String uid) {
        if (uid == "") {
            uid = jwtTokenUtil.getSubject(request);
        }
        UserRedis userRedis = userRedisService.findUserRedisById(uid);

        if (userRedis == null) {
            return new ResFormat(false, 400L, "해당 유저의 최근 위치가 없습니다.");
        }

        return new ResFormat(true, 200L, userRedis.getCurLocation());
    }

    @GetMapping
    public ResFormat getAllLocatoin(HttpServletRequest request) {
        String uid = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(uid);


        List<Location> allLocationByUser = locationService.findAllLocationByUser(userByUid);

        List<GetAllLocatoinDto> locations = new LinkedList<>();
        for (Location location:allLocationByUser
             ) {
            GetAllLocatoinDto getAllLocatoin = new GetAllLocatoinDto(location.getLatitude(), location.getLongitude(), location.getCreatedAt(), location.getUser().getName());
            locations.add(getAllLocatoin);
        }

        return new ResFormat(true, 200L, locations);
    }

    @PostMapping("/test")
    public ResFormat performanceTest() {
        List<Location> locationEntity = new LinkedList<>();

        for (int i=0; i<10000; i++){
            locationEntity.add(new Location(null,10.123,11.123,new Timestamp(new Date().getTime()),null));
        }

//        long start = System.currentTimeMillis();
//        for (Location location:
//                locationEntity) {
//            locationService.saveLocation(location);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("10000개 데이터 각각 Insert 수행시간: " + (end - start)*1000 + " s");
//
//
//
//        Long start = System.currentTimeMillis();
//        locationService.saveAll(locationEntity);
//        Long end = System.currentTimeMillis();
//        System.out.println("10000개 데이터 jpa saveAll 수행시간: " + (end - start)/1000 + " s");


        Long start = System.currentTimeMillis();
        locationJdbcRepository.saveAll(locationEntity);
        Long end = System.currentTimeMillis();
        System.out.println("10000개 데이터 Batch (batch block = 720) 수행시간: " + (end - start)/1000 + " s");




        return new ResFormat(true, 200L, null);
    }

}
