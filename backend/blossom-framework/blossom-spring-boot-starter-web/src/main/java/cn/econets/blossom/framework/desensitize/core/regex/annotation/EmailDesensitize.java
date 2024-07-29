package cn.econets.blossom.framework.desensitize.core.regex.annotation;

import cn.econets.blossom.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.econets.blossom.framework.desensitize.core.regex.handler.EmailDesensitizationHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * Mailbox desensitization annotation
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = EmailDesensitizationHandler.class)
public @interface EmailDesensitize {

    /**
     * Matching regular expression
     */
    String regex() default "(^.)[^@]*(@.*$)";

    /**
     * Replacement rules，Mailbox;
     *
     * For example：example@gmail.com After desensitization e****@gmail.com
     */
    String replacer() default "$1****$2";
}
