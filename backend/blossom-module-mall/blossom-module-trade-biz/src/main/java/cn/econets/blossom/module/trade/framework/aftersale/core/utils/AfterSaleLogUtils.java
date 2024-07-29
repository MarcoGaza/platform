package cn.econets.blossom.module.trade.framework.aftersale.core.utils;


import cn.econets.blossom.module.trade.framework.aftersale.core.aop.AfterSaleLogAspect;

import java.util.Map;

/**
 * Operation log tool class
 * Current main function，Provided to the business code，Record operation details and extended fields
 *
 */
public class AfterSaleLogUtils {

    public static void setAfterSaleInfo(Long id, Integer beforeStatus, Integer afterStatus) {
        setAfterSaleInfo(id, beforeStatus, afterStatus, null);
    }

    public static void setAfterSaleInfo(Long id, Integer beforeStatus, Integer afterStatus,
                                        Map<String, Object> exts) {
        AfterSaleLogAspect.setAfterSale(id, beforeStatus, afterStatus, exts);
    }

}
