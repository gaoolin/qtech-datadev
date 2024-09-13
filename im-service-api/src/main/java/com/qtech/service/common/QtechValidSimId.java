package com.qtech.service.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/03 15:09:46
 * desc   :
 */


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SimIdValidator.class)
public @interface QtechValidSimId {
    String message() default "Invalid simId format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}