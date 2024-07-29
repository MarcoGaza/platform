package cn.econets.blossom.module.trade.service.brokerage.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Total commission withdrawal BO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrokerageWithdrawSummaryRespBO {

    /**
     * User Number
     */
    private Long userId;

    /**
     * Number of withdrawals
     */
    private Integer count;
    /**
     * Withdrawal amount
     */
    private Integer price;

}
