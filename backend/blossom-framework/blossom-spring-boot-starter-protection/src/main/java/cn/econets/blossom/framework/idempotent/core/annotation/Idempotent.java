package cn.econets.blossom.framework.idempotent.core.annotation;

import cn.econets.blossom.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.econets.blossom.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Idempotent Annotation
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * Idempotent timeout，Default is 1 seconds
     *
     * Attention，If the execution time exceeds it，Requests will still come in
     */
    int timeout() default 1;
    /**
     * Time unit，Default is SECONDS seconds
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * Prompt information，Prompt in progress
     */
    String message() default "Repeat request，Please try again later";

    /**
     * Used Key Parser
     */
    Class<? extends IdempotentKeyResolver> keyResolver() default DefaultIdempotentKeyResolver.class;
    /**
     * Used Key Parameters
     */
    String keyArg() default "";

}
