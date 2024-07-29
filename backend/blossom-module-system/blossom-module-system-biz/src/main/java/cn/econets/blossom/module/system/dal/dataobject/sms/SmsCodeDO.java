package cn.econets.blossom.module.system.dal.dataobject.sms;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Mobile verification code DO
 *
 * idx_mobile Index：Based on {@link #mobile} Field
 *
 */
@TableName("system_sms_code")
@KeySequence("system_sms_code_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodeDO extends BaseDO {

    /**
     * Number
     */
    private Long id;
    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * Verification code
     */
    private String code;
    /**
     * Send scene
     *
     * Enumeration {@link SmsCodeDO}
     */
    private Integer scene;
    /**
     * Create IP
     */
    private String createIp;
    /**
     * The number of messages sent today
     */
    private Integer todayIndex;
    /**
     * Whether to use
     */
    private Boolean used;
    /**
     * Usage time
     */
    private LocalDateTime usedTime;
    /**
     * Use IP
     */
    private String usedIp;

}
