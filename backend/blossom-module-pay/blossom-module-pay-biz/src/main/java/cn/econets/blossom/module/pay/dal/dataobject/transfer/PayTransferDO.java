package cn.econets.blossom.module.pay.dal.dataobject.transfer;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferStatusRespEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

// TODO Details required review
/**
 * Transfer slip DO
 *
 *
 */
@TableName(value ="pay_transfer", autoResultMap = true)
@KeySequence("pay_transfer_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class PayTransferDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * Transfer Order Number
     *
     */
    private String no;

    /**
     * Application Number
     *
     * Relationship {@link PayAppDO#getId()}
     */
    private Long appId;

    /**
     * Transfer channel number
     *
     * Relationship {@link PayChannelDO#getId()}
     */
    private Long channelId;

    /**
     * Transfer channel code
     *
     * Enumeration {@link PayChannelEnum}
     */
    private String channelCode;

    // ========== Merchant related fields ==========
    /**
     * Merchant transfer order number
     *
     * For example，Internal system A Order number，It is necessary to ensure that each PayAppDO Only
     */
    private String merchantTransferId;

    // ========== Transfer related fields ==========

    /**
     * Type
     *
     * Enumeration {@link PayTransferTypeEnum}
     */
    private Integer type;

    /**
     * Transfer Title
     */
    private String subject;

    /**
     * Transfer amount，Unit：Points
     */
    private Integer price;

    /**
     * Recipient's name
     */
    private String userName;

    /**
     * Transfer Status
     *
     * Enumeration {@link PayTransferStatusRespEnum}
     */
    private Integer status;

    /**
     * Time when order transfer is successful
     */
    private LocalDateTime successTime;

    // ========== Alipay transfer related fields ==========
    /**
     * Alipay login number
     */
    private String alipayLogonId;


    // ========== WeChat transfer related fields ==========
    /**
     * WeChat openId
     */
    private String openid;

    // ========== Other fields ==========

    /**
     * Asynchronous notification address
     */
    private String notifyUrl;

    /**
     * User IP
     */
    private String userIp;

    /**
     * Additional parameters for channels
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> channelExtras;

    /**
     * Channel transfer order number
     */
    private String channelTransferNo;

    /**
     * Error code for calling the channel
     */
    private String channelErrorCode;
    /**
     * Error message when calling the channel
     */
    private String channelErrorMsg;

    /**
     * Channel synchronization/Content of asynchronous notification
     *
     */
    private String channelNotifyData;

}
