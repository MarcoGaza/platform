package cn.econets.blossom.module.trade.service.aftersale;


import cn.econets.blossom.module.trade.dal.dataobject.aftersale.AfterSaleLogDO;
import cn.econets.blossom.module.trade.service.aftersale.bo.AfterSaleLogCreateReqBO;

import java.util.List;

/**
 * Transaction and after-sales log Service Interface
 *
 */
public interface AfterSaleLogService {

    /**
     * Create after-sales log
     *
     * @param createReqBO Log Record
     */
    void createAfterSaleLog(AfterSaleLogCreateReqBO createReqBO);

    /**
     * Get after-sales log
     *
     * @param afterSaleId After-sales number
     * @return After-sales log
     */
    List<AfterSaleLogDO> getAfterSaleLogList(Long afterSaleId);

}
