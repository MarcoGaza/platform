package cn.econets.blossom.module.pay.dal.dataobject.order;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Payment order DO
 *
 *
 */
@TableName("pay_order")
@KeySequence("pay_order_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderDO extends BaseDO {

    /**
     * Order Number，Database auto-increment
     */
    private Long id;
    /**
     * Application Number
     *
     * Relationship {@link PayAppDO#getId()}
     */
    private Long appId;
    /**
     * Channel Number
     *
     * Relationship {@link PayChannelDO#getId()}
     */
    private Long channelId;
    /**
     * Channel Code
     *
     * Enumeration {@link PayChannelEnum}
     */
    private String channelCode;

    // ========== Merchant related fields ==========

    /**
     * Merchant order number
     *
     * For example，Internal System A Order number，It is necessary to ensure that each PayAppDO Only
     */
    private String merchantOrderId;
    /**
     * Product Title
     */
    private String subject;
    /**
     * Product description information
     */
    private String body;
    /**
     * Asynchronous notification address
     */
    private String notifyUrl;

    // ========== Order related fields ==========

    /**
     * Payment amount，Unit：Points
     */
    private Integer price;
    /**
     * Channel Fee，Unit：Percentage
     *
     * Redundant {@link PayChannelDO#getFeeRate()}
     */
    private Double channelFeeRate;
    /**
     * Channel fee amount，Unit：Points
     */
    private Integer channelFeePrice;
    /**
     * Payment Status
     *
     * Enumeration {@link PayOrderStatusEnum}
     */
    private Integer status;
    /**
     * User IP
     */
    private String userIp;
    /**
     * Order expiration time
     */
    private LocalDateTime expireTime;
    /**
     * Time when order payment is successful
     */
    private LocalDateTime successTime;
    /**
     * The order extension number of successful payment
     *
     * Relationship {@link PayOrderExtensionDO#getId()}
     */
    private Long extensionId;
    /**
     * External order number of successful payment
     *
     * Relationship {@link PayOrderExtensionDO#getNo()}
     */
    private String no;

    // ========== Refund related fields ==========
    /**
     * Total refund amount，Unit：Points
     */
    private Integer refundPrice;

    // ========== Channel related fields ==========
    /**
     * Channel User Number
     *
     * For example，WeChat openid、Alipay account
     */
    private String channelUserId;
    /**
     * Channel order number
     */
    private String channelOrderNo;

}
