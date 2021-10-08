package ajou.withme.locationData.service;

import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserByUid(String uid) {
        return userRepository.findByUid(uid);
    }
}
