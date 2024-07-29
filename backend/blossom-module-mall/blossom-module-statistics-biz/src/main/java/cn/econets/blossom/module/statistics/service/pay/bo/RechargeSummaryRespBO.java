package cn.econets.blossom.module.statistics.service.pay.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Recharge Statistics Response BO
 */
@Data
public class RechargeSummaryRespBO {

    /**
     * Number of top-up members
     */
    private Integer rechargeUserCount;

    /**
     * Recharge amount
     */
    private Integer rechargePrice;

}
