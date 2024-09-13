package com.qtech.service.config.reverse;

import com.qtech.service.utils.response.R;
import com.qtech.service.utils.response.ResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

@Aspect
@Component
public class CheckSimIdIgnoreAspect {
    private static final Logger logger = LoggerFactory.getLogger(CheckSimIdIgnoreAspect.class);
    @Autowired
    private RedisTemplate<String, Boolean> redisTemplate;

    @Pointcut("execution(* com.qtech.service.controller.chk.EqReverseCtrlInfoController.getEqReverseCtrlInfo(..))")
    public void checkSimIdPointcut() {
    }

    @Around(value = "checkSimIdPointcut()")
    public Object checkSimIdAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录请求参数
        Object[] args = joinPoint.getArgs();
        // HttpServletRequest request = (HttpServletRequest) args[1];
        String simId = URLDecoder.decode((String) args[0], StandardCharsets.UTF_8.name());
        // 打印原始请求路径
        logger.info(">>>>> Received request for path: {}", simId);

        try {
            if (Boolean.TRUE.equals(redisTemplate.hasKey(EQ_REVERSE_IGNORE_SIM_PREFIX + simId))) {
                logger.warn(">>>>> simId is ignored: " + simId);
                return new R<String>()
                        .setCode(ResponseCode.SUCCESS.getCode())
                        .setMsg("Equipment reverse ignored")
                        .setData(null);
            } else {
                return joinPoint.proceed();
            }
        } catch (ConstraintViolationException ex) {
            // 捕获并处理 ConstraintViolationException
            // logger.error("ConstraintViolationException caught in aspect: {}", ex.getMessage(), ex);
            // 返回自定义的错误响应
            return new R<String>()
                    .setCode(ResponseCode.SUCCESS.getCode())
                    .setMsg(ex.getConstraintViolations().iterator().next().getMessage())
                    .setData(null);
        }
    }
}