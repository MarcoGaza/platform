package cn.econets.blossom.module.statistics.service.trade.bo;

import lombok.Data;

/**
 * Order Statistics Response BO
 *
 */
@Data
public class TradeOrderSummaryRespBO {

    /**
     * Number of orders created
     */
    private Integer orderCreateCount;
    /**
     * Number of items in the paid order
     */
    private Integer orderPayCount;
    /**
     * Total payment amount，Unit：Points
     */
    private Integer orderPayPrice;

}
