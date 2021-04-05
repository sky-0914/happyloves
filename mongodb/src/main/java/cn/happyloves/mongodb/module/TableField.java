package cn.happyloves.mongodb.module;

/**
 * @author ZC
 * @date 2021/4/2 15:41
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段名注解。测试用
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    String value() default "";
}
