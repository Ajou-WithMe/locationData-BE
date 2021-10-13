package ajou.withme.locationData.controller;

import ajou.withme.locationData.domain.InitSafeZone;
import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.dto.SafeZone.request.SaveInitSafeZoneRequest;
import ajou.withme.locationData.service.InitSafeZoneService;
import ajou.withme.locationData.service.UserOptionService;
import ajou.withme.locationData.service.UserService;
import ajou.withme.locationData.util.JwtTokenUtil;
import ajou.withme.locationData.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
