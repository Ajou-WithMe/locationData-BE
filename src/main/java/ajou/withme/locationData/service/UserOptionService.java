package ajou.withme.locationData.service;

import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.domain.UserOption;
import ajou.withme.locationData.repository.UserOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOptionService {
    private final UserOptionRepository userOptionRepository;

    public UserOption findUserOptionByUser(User user) {
        return userOptionRepository.findByUser(user);
    }

    public UserOption saveUser(UserOption userOptionByUser) {
        return userOptionRepository.save(userOptionByUser);
    }
}
