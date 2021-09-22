package ajou.withme.locationData.service;

import ajou.withme.locationData.domain.UserRedis;
import ajou.withme.locationData.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRedisService {

    private final UserRedisRepository userRedisRepository;

    public UserRedis saveUserRedis(UserRedis userRedis) {
        return userRedisRepository.save(userRedis);
    }
}
