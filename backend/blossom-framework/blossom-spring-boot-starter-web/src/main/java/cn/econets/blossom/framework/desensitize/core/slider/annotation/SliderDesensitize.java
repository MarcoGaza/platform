package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.DefaultDesensitizationHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Sliding desensitization annotation
 *
 */
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = DefaultDesensitizationHandler.class)
public @interface SliderDesensitize {

    /**
     * Suffix reserved length
     */
    int suffixKeep() default 0;

    /**
     * Replacement rules，Will keep the prefix and suffix，Replace all with replacer
     *
     * For example：prefixKeep = 1; suffixKeep = 2; replacer = "*";
     * Original string  123456
     * After desensitization     1***56
     */
    String replacer() default "*";

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 0;
}
