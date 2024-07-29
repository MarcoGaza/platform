package cn.econets.blossom.framework.operatelog.core.annotations;

import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operation log annotation
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    // ========== Module fields ==========

    /**
     * Operation module
     *
     * When empty，Will try to read {@link Tag#name()} Properties
     */
    String module() default "";
    /**
     * Operation name
     *
     * When empty，Will try to read {@link Operation#summary()} Properties
     */
    String name() default "";
    /**
     * Operation Category
     *
     * It is not actually an array，Because enumeration cannot be set null As default value
     */
    OperateTypeEnum[] type() default {};

    // ========== Switch field ==========

    /**
     * Whether to record operation logs
     */
    boolean enable() default true;
    /**
     * Whether to record method parameters
     */
    boolean logArgs() default true;
    /**
     * Whether to record method result data
     */
    boolean logResultData() default true;

}
