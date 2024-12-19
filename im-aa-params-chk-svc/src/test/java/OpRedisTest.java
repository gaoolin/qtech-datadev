import com.qtech.check.config.redis.RedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static com.qtech.share.aa.constant.ComparisonConstants.EQ_REVERSE_IGNORE_SIM_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/11/29 15:47:01
 * desc   :
 */

@SpringBootTest(classes = {OpRedisTest.class, RedisConfig.class})
@RunWith(SpringRunner.class)
public class OpRedisTest {

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Test
    public void testGetValuesByPattern() {
        // 定义通配符模式
        // String pattern = "qtech:im:chk:ignored:*";

        String pattern = "qtech:im:aa:list:model:*";

        // Set<String> keys = stringRedisTemplate.keys(EQ_REVERSE_IGNORE_SIM_PREFIX + "865012064237919");
        // System.out.println(keys);

        // 使用 ScanOptions 来设置通配符模式
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(100).build();

        // 使用 Cursor 迭代器来遍历所有匹配的键
        try (Cursor<byte[]> cursor = stringRedisTemplate.getConnectionFactory().getConnection().scan(options)) {
            if (!cursor.hasNext()) {
                System.out.println("No keys found matching the pattern: " + pattern);
            } else {
                while (cursor.hasNext()) {
                    byte[] keyBytes = cursor.next();
                    String key = new String(keyBytes);

                    // 获取对应的值
                    String value = stringRedisTemplate.opsForValue().get(key);
                    stringRedisTemplate.delete(key);
                    System.out.println("Key: " + key + ", Value: " + value);
                }
            }
        }
    }
}