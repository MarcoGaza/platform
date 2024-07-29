package cn.econets.blossom.module.trade.service.config;

import cn.econets.blossom.module.trade.controller.admin.config.vo.TradeConfigSaveReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.config.TradeConfigDO;

import javax.validation.Valid;

/**
 * Trading Center Configuration Service Interface
 *
 */
public interface TradeConfigService {

    /**
     * Update trading center configuration
     *
     * @param updateReqVO Update information
     */
    void saveTradeConfig(@Valid TradeConfigSaveReqVO updateReqVO);

    /**
     * Get trading center configuration
     *
     * @return Trading Center Configuration
     */
    TradeConfigDO getTradeConfig();

}
