package ajou.withme.locationData.util;

import ajou.withme.locationData.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final AuthService authService;

    public String getSubject(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid");
        if (uid != null) {
            return uid;
        } else {
            String accessToken = request.getHeader("AccessToken");
            Claims claimsByToken = authService.getClaimsByToken(accessToken);
            return authService.getSubject(claimsByToken);
        }
    }
}
