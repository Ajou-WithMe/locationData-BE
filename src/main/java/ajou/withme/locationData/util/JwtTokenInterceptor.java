package ajou.withme.locationData.util;

import ajou.withme.locationData.domain.Auth;
import ajou.withme.locationData.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public JwtTokenInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String accessToken = request.getHeader("AccessToken");

        if (accessToken != null) {
            if (authService.isValidToken(accessToken)) {
                return true;
            } else {
                String uid = authService.getExpiredSubject(accessToken);
                Auth auth = authService.findAuthByAccessToken(accessToken);

                if (auth == null) {
                    response.setContentType("application/json");
                    response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");
                    return false;
                }

                if (!authService.isValidToken(auth.getRefreshToken())) {
                    response.setContentType("application/json");
                    response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");
                    return false;
                }

                if (uid.equals(authService.getSubject(authService.getClaimsByToken(auth.getRefreshToken())))) {
                    String newAccessToken = authService.createToken(uid, (long) (5 * 60 * 1000));
                    String newRefreshToken = authService.createToken(uid, (long) (14 * 24 * 60 * 60 * 1000));

                    authService.deleteAuthByUser(auth.getUser());
                    Auth newAuth = authService.createAuth(newAccessToken,newRefreshToken, auth.getUser());
                    authService.saveAuth(newAuth);

                    request.setAttribute("uid", auth.getUser().getUid());
                    response.setHeader("AccessToken", newAccessToken);
                }
                return true;
            }
        } else {
            response.setContentType("application/json");
            response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");
            return false;
        }
    }
}
