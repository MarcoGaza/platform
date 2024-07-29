package cn.econets.blossom.module.trade.service.brokerage.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Total user commission BO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBrokerageSummaryRespBO {

    /**
     * User Number
     */
    private Long userId;
    /**
     * Promotion quantity
     */
    private Integer count;
    /**
     * Total Commission
     */
    private Integer price;

}
