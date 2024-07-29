package cn.econets.blossom.module.system.dal.dataobject.sms;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.sms.SmsReceiveStatusEnum;
import cn.econets.blossom.module.system.enums.sms.SmsSendStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * SMS log DO
 *
 */
@TableName(value = "system_sms_log", autoResultMap = true)
@KeySequence("system_sms_log_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsLogDO extends BaseDO {

    /**
     * Self-increment number
     */
    private Long id;

    // ========= Channel related fields =========

    /**
     * SMS channel number
     *
     * Relationship {@link SmsChannelDO#getId()}
     */
    private Long channelId;
    /**
     * SMS channel code
     *
     * Redundant {@link SmsChannelDO#getCode()}
     */
    private String channelCode;

    // ========= Template related fields =========

    /**
     * Template number
     *
     * Relationship {@link SmsTemplateDO#getId()}
     */
    private Long templateId;
    /**
     * Template encoding
     *
     * Redundant {@link SmsTemplateDO#getCode()}
     */
    private String templateCode;
    /**
     * SMS type
     *
     * Redundant {@link SmsTemplateDO#getType()}
     */
    private Integer templateType;
    /**
     * Based on {@link SmsTemplateDO#getContent()} Formatted content
     */
    private String templateContent;
    /**
     * Based on {@link SmsTemplateDO#getParams()} Parameters after input
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;
    /**
     * SMS API Template number
     *
     * Redundant {@link SmsTemplateDO#getApiTemplateId()}
     */
    private String apiTemplateId;

    // ========= Mobile phone related fields =========

    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * User Number
     */
    private Long userId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;

    // ========= Send related fields =========

    /**
     * Send status
     *
     * Enumeration {@link SmsSendStatusEnum}
     */
    private Integer sendStatus;
    /**
     * Send time
     */
    private LocalDateTime sendTime;
    /**
     * SMS API The encoding of the sent result
     *
     * Because the third-party error code may be a string，So use String Type
     */
    private String apiSendCode;
    /**
     * SMS API Send failed prompt
     */
    private String apiSendMsg;
    /**
     * SMS API Send the only request returned ID
     *
     * Used for text messaging API Locate and debug
     */
    private String apiRequestId;
    /**
     * SMS API Send the returned sequence number
     *
     * Used for text messaging API Platform sending record association
     */
    private String apiSerialNo;

    // ========= Receive related fields =========

    /**
     * Receiving status
     *
     * Enumeration {@link SmsReceiveStatusEnum}
     */
    private Integer receiveStatus;
    /**
     * Receive time
     */
    private LocalDateTime receiveTime;
    /**
     * SMS API The code of the received result
     */
    private String apiReceiveCode;
    /**
     * SMS API Reminder for receiving results
     */
    private String apiReceiveMsg;

}
