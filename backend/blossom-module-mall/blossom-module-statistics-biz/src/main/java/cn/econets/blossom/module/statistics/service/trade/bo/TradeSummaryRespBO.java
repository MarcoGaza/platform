package cn.econets.blossom.module.statistics.service.trade.bo;

import lombok.Data;

/**
 * Transaction Statistics Resp BO
 *
 */
@Data
public class TradeSummaryRespBO {

    /**
     * Quantity
     */
    private Integer count;

    /**
     * Total
     */
    private Integer summary;

}
