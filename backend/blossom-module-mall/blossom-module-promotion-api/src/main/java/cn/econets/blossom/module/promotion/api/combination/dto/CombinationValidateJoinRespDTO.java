package cn.econets.blossom.module.promotion.api.combination.dto;

import lombok.Data;

/**
 * Check to participate in group buying Response DTO
 *
 */
@Data
public class CombinationValidateJoinRespDTO {

    /**
     * Bargaining activity number
     */
    private Long activityId;
    /**
     * Bargaining activity name
     */
    private String name;

    /**
     * Group purchase amount
     */
    private Integer combinationPrice;

}
