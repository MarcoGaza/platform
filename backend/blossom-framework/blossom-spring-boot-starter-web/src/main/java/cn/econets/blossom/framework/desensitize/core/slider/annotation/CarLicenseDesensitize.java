package cn.econets.blossom.framework.desensitize.core.slider.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.slider.handler.CarLicenseDesensitization;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * License plate number
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = CarLicenseDesensitization.class)
public @interface CarLicenseDesensitize {

    /**
     * Prefix reserved length
     */
    int prefixKeep() default 3;

    /**
     * Suffix retention length
     */
    int suffixKeep() default 1;

    /**
     * Replacement rules，License plate number;For example：CantoneseA66666 After desensitization, it is CantoneseA6***6
     */
    String replacer() default "*";

}
