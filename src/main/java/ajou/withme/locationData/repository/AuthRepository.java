package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.Auth;
import ajou.withme.locationData.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    void deleteByUser(User user);

    void deleteByRefreshToken(String refreshToken);

    Auth findByRefreshToken(String refreshToken);

    Auth findByAccessToken(String accessToken);
}
