package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.FixedPhoneDesensitization;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Landline
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = FixedPhoneDesensitization.class)
public @interface FixedPhoneDesensitize {

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 4;

    /**
     * Suffix retention length
     */
    int suffixKeep() default 2;

    /**
     * Replacement rules，Landline;For example：01086551122 After desensitization 0108*****22
     */
    String replacer() default "*";

}
