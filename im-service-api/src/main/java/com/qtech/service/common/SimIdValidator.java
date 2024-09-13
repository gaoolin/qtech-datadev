package com.qtech.service.common;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/03 15:11:22
 * desc   :
 */

@Component
public class SimIdValidator implements ConstraintValidator<QtechValidSimId, String> {
    @Override
    public void initialize(QtechValidSimId constraintAnnotation) {
        // 初始化方法，通常不需要实现
    }

    @Override
    public boolean isValid(String simId, ConstraintValidatorContext context) {
        // 实现SIM卡号的格式验证逻辑
        if (simId == null) {
            return false;
        }

        try {
            // 尝试对simId进行URL解码
            String decodedSimId = URLDecoder.decode(simId, StandardCharsets.UTF_8.name());
            // 校验解码后的simId是否符合86开头的纯数字格式
            return decodedSimId.matches("^86\\d+$");
        } catch (Exception e) {
            // 解码失败，视为无效
            return false;
        }
    }
}
