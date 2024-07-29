package cn.econets.blossom.module.trade.framework.aftersale.core.annotations;

import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleOperateTypeEnum;
import cn.econets.blossom.module.trade.framework.aftersale.core.aop.AfterSaleLogAspect;

import java.lang.annotation.*;

/**
 * Annotations to after-sales log
 *
 * When written in the method，will automatically record after-sales log
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterSaleLog {

    /**
     * Operation type
     */
    AfterSaleOperateTypeEnum operateType();

}
