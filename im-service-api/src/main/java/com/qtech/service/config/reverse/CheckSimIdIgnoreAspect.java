package com.qtech.service.config.reverse;

import com.qtech.service.exception.SimIdIgnoredException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.qtech.service.common.Constants.EQ_REVERSE_IGNORE_SIM_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/15 09:50:37
 * desc   :  定义SimIdCheckAspect切面，该切面会在控制器方法执行之前拦截请求，检查simId是否在Redis中存在。
 * <p>
 * SimIdCheckAspect 切面类中使用 @Around 注解来拦截getEqReverseCtrlInfo方法的执行。它首先检查simId是否存在于Redis中。
 * 如果存在，则直接返回一个成功响应R<String>，否则继续执行控制器的方法。
 */

@Slf4j
@Aspect
@Component
public class CheckSimIdIgnoreAspect {

    @Autowired
    private RedisTemplate<String, Boolean> redisTemplate;

    @Pointcut("execution(public * com.qtech.service.controller.chk.EqReverseCtrlInfoController.getEqReverseCtrlInfo(..)) && args(simId)")
    public void checkSimIdPointcut(String simId) {
    }

    @Around(value = "checkSimIdPointcut(simId)", argNames = "joinPoint,simId")
    public Object checkSimId(ProceedingJoinPoint joinPoint, String simId) throws Throwable {
        Boolean isIgnored = redisTemplate.opsForValue().get(EQ_REVERSE_IGNORE_SIM_PREFIX + simId);
        if (Boolean.TRUE.equals(isIgnored)) {
            log.info("simId is ignored: " + simId);
            throw new SimIdIgnoredException();
        }
        return joinPoint.proceed();
    }
}