package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.MobileDesensitization;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Mobile phone number
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = MobileDesensitization.class)
public @interface MobileDesensitize {

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 3;

    /**
     * Suffix retention length
     */
    int suffixKeep() default 4;

    /**
     * Replacement rules，Mobile phone number;For example：13248765917 After desensitization 132****5917
     */
    String replacer() default "*";

}
