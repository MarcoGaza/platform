package cn.econets.blossom.module.crm.framework.permission.core.annotations;

import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

/**
 * CRM Data operation permission verification AOP Annotation
 *
 */
@Target({METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrmPermission {

    /**
     * CRM Type
     */
    CrmBizTypeEnum[] bizType() default {};

    /**
     * CRM Type extension，Passed Spring EL Expression obtained {@link #bizType()}
     *
     * Purpose：Used for CrmPermissionController Team permission verification
     */
    String bizTypeValue() default "";

    /**
     * Data number，Pass Spring EL Expression acquisition
     */
    String bizId();

    /**
     * Required permission level for operation
     */
    CrmPermissionLevelEnum level();

}
