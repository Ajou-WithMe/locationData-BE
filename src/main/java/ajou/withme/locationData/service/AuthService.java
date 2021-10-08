package ajou.withme.locationData.service;

import ajou.withme.locationData.domain.Auth;
import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.repository.AuthRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    @Value("${KEY.JWT}")
    private String SECRETKEY;

    public String createToken(String subject, Long expTime) {
        if (expTime <= 0) {
            throw new RuntimeException("만료시간이 0보다 작습니다.");
        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);

        // key 생성
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    public Auth saveAuth(Auth auth) {
        return authRepository.save(auth);
    }

    public String getSubject(Claims claims) {
        return claims.getSubject();
    }

    public Claims getClaimsByToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRETKEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidToken(String token) {
        try {
            getClaimsByToken(token);
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
            return false;
        } catch (JwtException | NullPointerException exception) {
            System.out.println("exception = " + exception);
            return false;
        }
    }

    public void deleteAuthByUser(User user) {
        authRepository.deleteByUser(user);
    }

    public Auth createAuth(String accessToken,String refreshToken, User user) {
        return Auth.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
    }

    public String getExpiredSubject(String accessToken) {
        try {
            return getSubject(getClaimsByToken(accessToken));
        } catch (ExpiredJwtException exception) {
            return exception.getClaims().getSubject();
        }
    }

    public Auth findAuthByAccessToken(String accessToken) {
        return authRepository.findByAccessToken(accessToken);
    }

    public void deleteAuthByRefreshToken(String refreshToken) {
        authRepository.deleteByRefreshToken(refreshToken);
    }

}
