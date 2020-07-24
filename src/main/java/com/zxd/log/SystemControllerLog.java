package com.zxd.log;/**
 * Created by Think on 2016/10/14.
 */

import java.lang.annotation.*;

/**
 *
 * <br> @version 1.0
 * <br> 自定义注解用于日志记录
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    String description() default "";
    int params() default 0;
}
