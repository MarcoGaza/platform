package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.PasswordDesensitization;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Password
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = PasswordDesensitization.class)
public @interface PasswordDesensitize {

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 0;

    /**
     * Suffix retention length
     */
    int suffixKeep() default 0;

    /**
     * Replacement rules，Password;
     *
     * For example：123456 After desensitization ******
     */
    String replacer() default "*";

}
