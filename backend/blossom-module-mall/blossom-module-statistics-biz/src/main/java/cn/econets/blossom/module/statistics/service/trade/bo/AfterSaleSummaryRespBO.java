package cn.econets.blossom.module.statistics.service.trade.bo;

import lombok.Data;

/**
 * After-sales statistics Response DTO
 *
 */
@Data
public class AfterSaleSummaryRespBO {

    /**
     * Number of refunded orders
     */
    private Integer afterSaleCount;
    /**
     * Total refund amount，Unit：Points
     */
    private Integer afterSaleRefundPrice;

}
