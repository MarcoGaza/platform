package cn.econets.blossom.module.pay.dal.dataobject.wallet;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Member wallet recharge
 */
@TableName(value ="pay_wallet_recharge")
@KeySequence("pay_wallet_recharge_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
public class PayWalletRechargeDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * Wallet Number
     *
     * Relationship {@link PayWalletDO#getId()}
     */
    private Long walletId;

    /**
     * Actual balance of the user
     *
     * For example, charging 100 Send 20，The value is 120
     */
    private Integer totalPrice;
    /**
     * Actual payment amount
     */
    private Integer payPrice;
    /**
     * Wallet gift amount
     */
    private Integer bonusPrice;

    /**
     * Recharge package number
     *
     * Relationship {@link PayWalletRechargeDO#getPackageId()} Field
     */
    private Long packageId;

    /**
     * Has it been paid?
     *
     * true - Paid
     * false - Not paid
     */
    private Boolean payStatus;

    /**
     * Payment order number
     *
     * Relationship {@link PayOrderDO#getId()}
     */
    private Long payOrderId;

    /**
     * Payment channel for successful payment
     *
     * Redundant {@link PayOrderDO#getChannelCode()}
     */
    private String payChannelCode;
    /**
     * Order payment time
     */
    private LocalDateTime payTime;

    /**
     * Payment refund order number
     *
     * Relationship {@link PayRefundDO#getId()}
     */
    private Long payRefundId;

    /**
     * Refund amount，Including gift amount
     */
    private Integer refundTotalPrice;
    /**
     * Refund payment amount
     */
    private Integer refundPayPrice;

    /**
     * Refund wallet gift amount
     */
    private Integer refundBonusPrice;

    /**
     * Refund Time
     */
    private LocalDateTime refundTime;

    /**
     * Refund Status
     *
     * Enumeration {@link PayRefundStatusEnum}
     */
    private Integer refundStatus;

}
