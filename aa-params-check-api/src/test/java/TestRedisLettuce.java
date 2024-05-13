/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/10 13:57:15
 * desc   :  TODO
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisLettuce.class)
public class TestRedisLettuce {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void t1() {
        String key = "key1";
        System.out.println("插入数据到redis");
        redisTemplate.opsForValue().set(key, "value1");
        Object value = redisTemplate.opsForValue().get(key);
        System.out.println("从redis中获取到值为 " + value);
        /*Boolean delete = redisTemplate.delete(key);
        System.out.println("删除redis中值 " + delete);*/
    }
}

