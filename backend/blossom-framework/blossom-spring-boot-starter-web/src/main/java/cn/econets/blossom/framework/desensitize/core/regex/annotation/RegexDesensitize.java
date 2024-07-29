package cn.econets.blossom.framework.desensitize.core.regex.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.regex.handler.DefaultRegexDesensitizationHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Regular desensitization annotation
 *
 */
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = DefaultRegexDesensitizationHandler.class)
public @interface RegexDesensitize {

    /**
     * Matching regular expression（Match all by default）
     */
    String regex() default "^[\\s\\S]*$";

    /**
     * Replacement rules，Will replace all matched strings with replacer
     *
     * For example：regex=123; replacer=******
     * Original string 123456789
     * Anonymous string ******456789
     */
    String replacer() default "******";
}
