package ajou.withme.locationData;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RedisTest {


    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisTest(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Test
    public void testStrings() {
        final String key = "sabarada";

        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(key, "1"); // redis set 명령어
        final String result_1 = stringStringValueOperations.get(key); // redis get 명령어

        System.out.println("result_1 = " + result_1);

        stringStringValueOperations.increment(key); // redis incr 명령어
        final String result_2 = stringStringValueOperations.get(key);

        System.out.println("result_2 = " + result_2);
    }

    @Test
    public void testList() {
        final String key = "sabarada";

        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();

        stringStringListOperations.rightPush(key, "H");
        stringStringListOperations.rightPush(key, "e");
        stringStringListOperations.rightPush(key, "l");
        stringStringListOperations.rightPush(key, "l");
        stringStringListOperations.rightPush(key, "o");

        stringStringListOperations.rightPushAll(key, " ", "s", "a", "b", "a");

        final String character_1 = stringStringListOperations.index(key, 1);

        System.out.println("character_1 = " + character_1);

        final Long size = stringStringListOperations.size(key);

        System.out.println("size = " + size);

        final List<String> ResultRange = stringStringListOperations.range(key, 0, 9);

        assert ResultRange != null;
        System.out.println("ResultRange = " + Arrays.toString(ResultRange.toArray()));
    }
}
