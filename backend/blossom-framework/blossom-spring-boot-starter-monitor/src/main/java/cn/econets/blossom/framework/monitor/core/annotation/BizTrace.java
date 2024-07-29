package cn.econets.blossom.framework.monitor.core.annotation;

import java.lang.annotation.*;

/**
 * Print business number / Business type annotation
 *
 * When using，Need to set SkyWalking OAP Server of application.yaml Configuration file，Modify SW_SEARCHABLE_TAG_KEYS Configuration item，
 * Increase biz.type Japanese biz.id Two values，Then restart SkyWalking OAP Server Server。
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BizTrace {

    /**
     * Business Number tag Name
     */
    String ID_TAG = "biz.id";
    /**
     * Business Type tag Name
     */
    String TYPE_TAG = "biz.type";

    /**
     * @return Operation name
     */
    String operationName() default "";

    /**
     * @return Business Number
     */
    String id();

    /**
     * @return Business Type
     */
    String type();

}
