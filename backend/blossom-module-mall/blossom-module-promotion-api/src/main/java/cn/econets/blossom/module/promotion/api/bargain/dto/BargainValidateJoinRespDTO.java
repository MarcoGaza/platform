package cn.econets.blossom.module.promotion.api.bargain.dto;

import lombok.Data;

/**
 * Check and participate in bargaining Response DTO
 */
@Data
public class BargainValidateJoinRespDTO {

    /**
     * Bargaining activity number
     */
    private Long activityId;
    /**
     * Bargaining activity name
     */
    private String name;

    /**
     * Bargaining amount
     */
    private Integer bargainPrice;

}
