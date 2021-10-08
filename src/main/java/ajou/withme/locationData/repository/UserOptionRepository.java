package ajou.withme.locationData.repository;

import ajou.withme.locationData.domain.User;
import ajou.withme.locationData.domain.UserOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOptionRepository extends JpaRepository<UserOption,Long> {
    UserOption findByUser(User user);
}
