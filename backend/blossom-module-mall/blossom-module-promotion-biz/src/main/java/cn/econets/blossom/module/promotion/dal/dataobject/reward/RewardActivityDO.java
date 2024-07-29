package cn.econets.blossom.module.promotion.dal.dataobject.reward;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.module.promotion.enums.common.PromotionActivityStatusEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionConditionTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Save a lot of money and get a free gift DO
 *
 */
@TableName(value = "promotion_reward_activity", autoResultMap = true)
@KeySequence("promotion_reward_activity_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class RewardActivityDO extends BaseDO {

    /**
     * Activity number，Primary key auto-increment
     */
    @TableId
    private Long id;
    /**
     * Activity Title
     */
    private String name;
    /**
     * Status
     *
     * Enumeration {@link PromotionActivityStatusEnum}
     */
    private Integer status;
    /**
     * Start time
     */
    private LocalDateTime startTime;
    /**
     * End time
     */
    private LocalDateTime endTime;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Condition type
     *
     * Enumeration {@link PromotionConditionTypeEnum}
     */
    private Integer conditionType;
    /**
     * Product Range
     *
     * Enumeration {@link PromotionProductScopeEnum}
     */
    private Integer productScope;
    /**
     * Products SPU Numbered array
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> productSpuIds;
    /**
     * Array of preferential rules
     */
    @TableField(typeHandler = RuleTypeHandler.class)
    private List<Rule> rules;

    /**
     * Promotion Rules
     */
    @Data
    public static class Rule implements Serializable {

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

    // TODO ：You can find some new ideas
    public static class RuleTypeHandler extends AbstractJsonTypeHandler<List<Rule>> {

        @Override
        protected List<Rule> parse(String json) {
            return JsonUtils.parseArray(json, Rule.class);
        }

        @Override
        protected String toJson(List<Rule> obj) {
            return JsonUtils.toJsonString(obj);
        }

    }

}
