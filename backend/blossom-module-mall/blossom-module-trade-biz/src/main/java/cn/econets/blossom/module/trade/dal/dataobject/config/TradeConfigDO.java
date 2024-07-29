package cn.econets.blossom.module.trade.dal.dataobject.config;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.IntegerListTypeHandler;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageBindModeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageEnabledConditionEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Trading Center Configuration DO
 *
 */
@TableName(value = "trade_config", autoResultMap = true)
@KeySequence("trade_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeConfigDO extends BaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;

    // ========== After-sales related ==========

    /**
     * Reason for after-sales refund
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> afterSaleRefundReasons;
    /**
     * Reasons for after-sales returns
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> afterSaleReturnReasons;

    // ========== Delivery related ==========

    /**
     * Whether to enable free shipping for the entire site
     */
    private Boolean deliveryExpressFreeEnabled;
    /**
     * Minimum amount for free shipping，Unit：Points
     */
    private Integer deliveryExpressFreePrice;

    /**
     * Whether to enable self-collection
     */
    private Boolean deliveryPickUpEnabled;

    // ========== Distribution related ==========

    /**
     * Whether to enable commission sharing
     */
    private Boolean brokerageEnabled;
    /**
     * Commission model
     * <p>
     * Enumeration {@link BrokerageEnabledConditionEnum Corresponding class}
     */
    private Integer brokerageEnabledCondition;
    /**
     * Distribution relationship binding mode
     * <p>
     * Enumeration {@link BrokerageBindModeEnum Corresponding class}
     */
    private Integer brokerageBindMode;
    /**
     * Distribution poster image address array
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> brokeragePosterUrls;
    /**
     * First-level rebate ratio
     */
    private Integer brokerageFirstPercent;
    /**
     * Second level rebate ratio
     */
    private Integer brokerageSecondPercent;
    /**
     * Minimum amount for user withdrawal
     */
    private Integer brokerageWithdrawMinPrice;
    /**
     * User withdrawal fee percentage
     */
    private Integer brokerageWithdrawFeePercent;
    /**
     * Commission freeze time(Sky)
     */
    private Integer brokerageFrozenDays;
    /**
     * Withdrawal method
     * <p>
     * Enumeration {@link BrokerageWithdrawTypeEnum Corresponding class}
     */
    @TableField(typeHandler = IntegerListTypeHandler.class)
    private List<Integer> brokerageWithdrawTypes;

}
