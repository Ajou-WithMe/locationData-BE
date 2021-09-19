package ajou.withme.locationData;

import ajou.withme.locationData.domain.Person;
import ajou.withme.locationData.repository.PersonRedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisRepositoryTest {

    private final PersonRedisRepository redisRepository;

    @Autowired
    public RedisRepositoryTest(PersonRedisRepository personRedisRepository) {
        this.redisRepository = personRedisRepository;
    }

    @Test
    public void basicSave() {
        // given
        Person person = new Person(null, "fisrt", "last", "경기도");

        // when
        Person savedPerson = redisRepository.save(person);

        // then
        Optional<Person> findPerson = redisRepository.findById(savedPerson.getId());

        assertThat(findPerson.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(findPerson.get().getFirstname()).isEqualTo(person.getFirstname());
    }

}
