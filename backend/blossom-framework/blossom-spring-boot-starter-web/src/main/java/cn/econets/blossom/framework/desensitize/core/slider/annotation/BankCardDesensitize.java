package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.BankCardDesensitization;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Bank card number
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = BankCardDesensitization.class)
public @interface BankCardDesensitize {

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 6;

    /**
     * Suffix retention length
     */
    int suffixKeep() default 2;

    /**
     * Replacement rules，Bank card number; For example：9988002866797031 After desensitization 998800********31
     */
    String replacer() default "*";

}
