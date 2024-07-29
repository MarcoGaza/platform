package cn.econets.blossom.module.statistics.service.trade.bo;

import lombok.Data;

/**
 * Wallet Statistics Response DTO
 *
 */
@Data
public class WalletSummaryRespBO {

    /**
     * Total payment amount（Balance），Unit：Points
     */
    private Integer walletPayPrice;

    /**
     * Number of recharge orders
     */
    private Integer rechargePayCount;
    /**
     * Recharge amount，Unit：Points
     */
    private Integer rechargePayPrice;
    /**
     * Number of recharge refund orders
     */
    private Integer rechargeRefundCount;
    /**
     * Recharge refund amount，Unit：Points
     */
    private Integer rechargeRefundPrice;

}
