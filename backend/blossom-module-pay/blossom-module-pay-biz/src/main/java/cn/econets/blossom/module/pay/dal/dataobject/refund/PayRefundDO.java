package cn.econets.blossom.module.pay.dal.dataobject.refund;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Payment Refund Order DO
 * A payment order，You can have multiple payment refund orders
 *
 * That is PayOrderDO : PayRefundDO = 1 : n
 *
 *
 */
@TableName("pay_refund")
@KeySequence("pay_refund_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRefundDO extends BaseDO {

    /**
     * Refund order number，Database auto-increment
     */
    @TableId
    private Long id;
    /**
     * External refund number，Generate according to rules
     *
     * When calling the payment channel，Use this field as the refund number for docking：
     * 1. WeChat refund：Corresponding <a href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_4">Apply for a refund</a> of out_refund_no Field
     * 2. Alipay refund：Corresponding <a href="https://opendocs.alipay.com/open/02e7go"Unified acquiring transaction refund interface></a> of out_request_no Field
     */
    private String no;

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
     * Merchant Code
     *
     * Enumeration {@link PayChannelEnum}
     */
    private String channelCode;
    /**
     * Order Number
     *
     * Relationship {@link PayOrderDO#getId()}
     */
    private Long orderId;
    /**
     * Payment order number
     *
     * Redundant {@link PayOrderDO#getNo()}
     */
    private String orderNo;

    // ========== Merchant related fields ==========
    /**
     * Merchant order number
     *
     * For example，Internal system A Order number，It is necessary to ensure that each PayAppDO Only
     */
    private String merchantOrderId;
    /**
     * Merchant refund order number
     *
     * For example，Internal system A Order number，It is necessary to ensure that each PayAppDO Only
     */
    private String merchantRefundId;
    /**
     * Asynchronous notification address
     */
    private String notifyUrl;

    // ========== Refund related fields ==========
    /**
     * Refund Status
     *
     * Enumeration {@link PayRefundStatusEnum}
     */
    private Integer status;

    /**
     * Payment amount，Unit：Points
     */
    private Integer payPrice;
    /**
     * Refund amount，Unit：Points
     */
    private Integer refundPrice;

    /**
     * Refund reason
     */
    private String reason;

    /**
     * User IP
     */
    private String userIp;

    // ========== Channel related fields ==========
    /**
     * Channel order number
     *
     * Redundant {@link PayOrderDO#getChannelOrderNo()}
     */
    private String channelOrderNo;
    /**
     * Channel refund order number
     *
     * 1. WeChat refund：Corresponding <a href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_4">Apply for a refund</a> of refund_id Field
     * 2. Alipay refund：No fields
     */
    private String channelRefundNo;
    /**
     * Refund success time
     */
    private LocalDateTime successTime;

    /**
     * Error code for calling the channel
     */
    private String channelErrorCode;
    /**
     * Error message when calling the channel
     */
    private String channelErrorMsg;

    /**
     * Payment channel synchronization/Content of asynchronous notification
     *
     * Corresponding {@link PayRefundRespDTO#getRawData()}
     */
    private String channelNotifyData;

}
