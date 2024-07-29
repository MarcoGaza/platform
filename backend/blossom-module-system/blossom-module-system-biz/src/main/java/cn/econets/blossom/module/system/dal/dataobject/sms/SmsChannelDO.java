package cn.econets.blossom.module.system.dal.dataobject.sms;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.framework.sms.core.enums.SmsChannelEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SMS channel DO
 *
 */
@TableName(value = "system_sms_channel", autoResultMap = true)
@KeySequence("system_sms_channel_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmsChannelDO extends BaseDO {

    /**
     * Channel Number
     */
    private Long id;
    /**
     * SMS signature
     */
    private String signature;
    /**
     * Channel Code
     *
     * Enumeration {@link SmsChannelEnum}
     */
    private String code;
    /**
     * Enabled status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Remarks
     */
    private String remark;
    /**
     * SMS API Account
     */
    private String apiKey;
    /**
     * SMS API Key
     */
    private String apiSecret;
    /**
     * SMS sending callback URL
     */
    private String callbackUrl;

}
