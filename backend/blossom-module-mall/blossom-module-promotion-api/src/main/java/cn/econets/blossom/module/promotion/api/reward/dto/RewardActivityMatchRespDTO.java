package cn.econets.blossom.module.promotion.api.reward.dto;

import cn.econets.blossom.module.promotion.enums.common.PromotionConditionTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * Matching of full discount activities Response DTO
 *
 */
@Data
public class RewardActivityMatchRespDTO {

    /**
     * Activity number，Primary key auto-increment
     */
    private Long id;
    /**
     * Activity Title
     */
    private String name;
    /**
     * Condition type
     *
     * Enumeration {@link PromotionConditionTypeEnum}
     */
    private Integer conditionType;
    /**
     * Array of preferential rules
     */
    private List<Rule> rules;

    /**
     * Products SPU Numbered array
     */
    private List<Long> spuIds;

    // TODO Behind RewardActivityRespDTO After having it，Rule You can let it go
    /**
     * Promotion Rules
     */
    @Data
    public static class Rule {

        /**
         * Preferential threshold
         *
         * 1. Full N Yuan，Unit：Points
         * 2. Full N Item
         */
        private Integer limit;
        /**
         * Preferential price，Unit：Points
         */
        private Integer discountPrice;
        /**
         * Is shipping free?
         */
        private Boolean freeDelivery;
        /**
         * Gifted points
         */
        private Integer point;
        /**
         * Array of coupon numbers given
         */
        private List<Long> couponIds;
        /**
         * Array of the number of coupons given
         */
        private List<Integer> couponCounts;

    }

}
