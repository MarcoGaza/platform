package cn.econets.blossom.framework.permission.core.annotation;

import cn.econets.blossom.framework.permission.core.rule.DataPermissionRule;

import java.lang.annotation.*;

/**
 * Data permission annotation
 * Can be declared on a class or method，Data permission rules used by the identifier
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * Whether the current class or method has data permissions enabled
     * Even if not added @DataPermission Annotation，The default is on
     * Can be set enable for false Disable
     */
    boolean enable() default true;

    /**
     * Effective data permission rule array，Priority is higher than {@link #excludeRules()}
     */
    Class<? extends DataPermissionRule>[] includeRules() default {};

    /**
     * Array of excluded data permission rules，Lowest priority
     */
    Class<? extends DataPermissionRule>[] excludeRules() default {};

}
