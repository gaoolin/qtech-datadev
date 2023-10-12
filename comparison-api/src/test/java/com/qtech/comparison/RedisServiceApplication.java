package com.qtech.comparison;

import com.qtech.comparison.service.IComparisonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 10:09:32
 * desc   :
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceApplication {

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    IComparisonService wbComparisonService;

    @Test
    public void test(){

        ValueOperations<String, String> valueOperations = stringStringRedisTemplate.opsForValue();
        valueOperations.set("key", "hello", 60, TimeUnit.SECONDS);
    }

    @Test
    public void testGetComparisonResult() {
        String comparisonResult = wbComparisonService.getComparisonResult("", "861480034800625");
        System.out.println(comparisonResult);
    }
}
