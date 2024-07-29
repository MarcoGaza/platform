package cn.econets.blossom.module.trade.service.price;

import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;

import javax.validation.Valid;

/**
 * Price calculation Service Interface
 *
 */
public interface TradePriceService {

    /**
     * Price calculation
     *
     * @param calculateReqDTO Calculation information
     * @return Calculation results
     */
    TradePriceCalculateRespBO calculatePrice(@Valid TradePriceCalculateReqBO calculateReqDTO);

}
