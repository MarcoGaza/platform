package cn.econets.blossom.module.pay.dal.dataobject.order;

import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.Map;

/**
 * Payment order extension DO
 *
 * Each time the payment channel is called，A corresponding record will be generated
 *
 *
 */
@TableName(value = "pay_order_extension",autoResultMap = true)
@KeySequence("pay_order_extension_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderExtensionDO extends BaseDO {

    /**
     * Order extension number，Database auto-increment
     */
    private Long id;
    /**
     * External order number，Generate according to rules
     *
     * When calling the payment channel，Use this field as the docking order number：
     * 1. WeChat Pay：Corresponding <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_1.shtml">JSAPI Payment</a> of out_trade_no Field
     * 2. Alipay payment：Corresponding <a href="https://opendocs.alipay.com/open/270/105898">Computer website payment</a> of out_trade_no Field
     *
     * For example，P202110132239124200055
     */
    private String no;
    /**
     * Order Number
     *
     * Relationship {@link PayOrderDO#getId()}
     */
    private Long orderId;
    /**
     * Channel Number
     *
     * Relationship {@link PayChannelDO#getId()}
     */
    private Long channelId;
    /**
     * Channel Code
     */
    private String channelCode;
    /**
     * User IP
     */
    private String userIp;
    /**
     * Payment Status
     *
     * Enumeration {@link PayOrderStatusEnum}
     */
    private Integer status;
    /**
     * Additional parameters for payment channels
     *
     * See <a href="https://www.pingxx.com/api/Payment channel%20extra%20Parameter Description.html">Parameter Description</>
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> channelExtras;

    /**
     * Error code for calling the channel
     */
    private String channelErrorCode;
    /**
     * When calling a channel, an error is reported，Error message
     */
    private String channelErrorMsg;

    /**
     * Payment channel synchronization/Content of asynchronous notification
     *
     * Corresponding {@link PayOrderRespDTO#getRawData()}
     */
    private String channelNotifyData;

}
