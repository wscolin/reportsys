package com.zxd.log;/**
 * Created by Think on 2016/10/14.
 */

import java.lang.annotation.*;

/**
 * 版权声明 中科金审（北京）有限公司 版权所有 违者必究
 * <br> Company：中科金审
 * <br> @author 杜波
 * <br> 2016/10/14
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
