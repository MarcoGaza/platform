package cn.econets.blossom.module.trade.framework.order.core.annotations;

import cn.econets.blossom.module.trade.enums.order.TradeOrderOperateTypeEnum;
import cn.econets.blossom.module.trade.framework.order.core.aop.TradeOrderLogAspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Transaction order operation log AOP Annotation
 *
 */
@Target({METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TradeOrderLog {

    /**
     * Operation type
     */
    TradeOrderOperateTypeEnum operateType();

}
