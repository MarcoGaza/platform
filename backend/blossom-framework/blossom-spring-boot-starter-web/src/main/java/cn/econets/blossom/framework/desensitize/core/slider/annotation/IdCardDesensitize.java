package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.IdCardDesensitization;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * ID card
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = IdCardDesensitization.class)
public @interface IdCardDesensitize {

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 6;

    /**
     * Suffix reserved length
     */
    int suffixKeep() default 2;

    /**
     * Replacement rules，ID number;For example：530321199204074611 After desensitization 530321**********11
     */
    String replacer() default "*";

}
