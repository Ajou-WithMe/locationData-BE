package ajou.withme.locationData.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@RedisHash("people")
public class Person {

    @Id
    String id;
    String firstname;
    String lastname;
    String address;

}
