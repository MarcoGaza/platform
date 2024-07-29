package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.ChineseNameDesensitization;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Chinese name
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = ChineseNameDesensitization.class)
public @interface ChineseNameDesensitize {

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 1;

    /**
     * Suffix retention length
     */
    int suffixKeep() default 0;

    /**
     * Replacement rules，Chinese name;For example：Liu Zihao became Liu after desensitization**
     */
    String replacer() default "*";

}
