package ajou.withme.locationData.controller;

import ajou.withme.locationData.domain.*;
import ajou.withme.locationData.dto.LocationDto;
import ajou.withme.locationData.dto.SafeZone.request.SaveInitSafeZoneRequest;
import ajou.withme.locationData.dto.visitOften.response.FindVisitOftenDto;
import ajou.withme.locationData.dto.visitOften.response.MissingUserResonse;
import ajou.withme.locationData.service.*;
import ajou.withme.locationData.util.JwtTokenUtil;
import ajou.withme.locationData.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/safeZone")
@RequiredArgsConstructor
public class SafeZoneController {

    private final UserOptionService userOptionService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final InitSafeZoneService initSafeZoneService;
    private final ZoneLocationService zoneLocationService;
    private final VisitOftenService visitOftenService;
    private final UserRedisService userRedisService;

    @GetMapping
    public ResFormat findSafeZone(@RequestParam String uid) {
        User userByUid = userService.findUserByUid(uid);

        List<LocationDto> locationDtos = new LinkedList<>();

        List<Object[]> allLocationByUserId = zoneLocationService.findAllLocationByUserId(userByUid.getId());
        for (Object[] location:
                allLocationByUserId) {
            LocationDto locationDto = new LocationDto((double)location[0], (double)location[1]);
            locationDtos.add(locationDto);
        }

        return new ResFormat(true, 200L, locationDtos);
    }

    @GetMapping("/missing")
    public ResFormat findVisitOften(@RequestParam String uid) {
        User userByUid = userService.findUserByUid(uid);

        List<FindVisitOftenDto> visitOftenDto = visitOftenService.findVisitOftenDto(userByUid.getId());

        UserOption userOptionByUser = userOptionService.findUserOptionByUser(userByUid);
        Long time = (System.currentTimeMillis() - userOptionByUser.getCurrentNetwork().getTime()) / 1000; // 초

        UserRedis userRedisById = userRedisService.findUserRedisById(uid);
        Double speed = userRedisById.getSpeed(); // 시속

        Double distance = time * speed / 3.6; // 미터

        return new ResFormat(true, 200L, new MissingUserResonse(visitOftenDto, distance));
    }

    @PostMapping("/init")
    public ResFormat saveInitSafeZone(HttpServletRequest request, @RequestBody SaveInitSafeZoneRequest saveInitSafeZoneRequest) {
        String uid = jwtTokenUtil.getSubject(request);
        User userByUid = userService.findUserByUid(uid);

        List<InitSafeZone> safeZones = new LinkedList<>();

        for (SaveInitSafeZoneRequest.LatLng latLng:
             saveInitSafeZoneRequest.getSafeZone()) {
            InitSafeZone initSafeZone = new InitSafeZone(null, latLng.getLongitude(), latLng.getLatitude(), userByUid);
            safeZones.add(initSafeZone);
        }

        initSafeZoneService.saveInitSafeZone(safeZones);

        return new ResFormat(true, 201L, "초기 SafeZone 생성을 완료했습니다.");

    }
}
