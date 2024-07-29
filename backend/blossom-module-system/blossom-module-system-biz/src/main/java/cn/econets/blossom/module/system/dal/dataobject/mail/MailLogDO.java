package cn.econets.blossom.module.system.dal.dataobject.mail;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.mail.MailSendStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Mailbox log DO
 * Record every email sent
 *
 */
@TableName(value = "system_mail_log", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailLogDO extends BaseDO implements Serializable {

    /**
     * Log number，Self-increment
     */
    private Long id;

    /**
     * User code
     */
    private Long userId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * Receiving email address
     */
    private String toMail;

    /**
     * Email account number
     *
     * Association {@link MailAccountDO#getId()}
     */
    private Long accountId;
    /**
     * Send email address
     *
     * Redundant {@link MailAccountDO#getMail()}
     */
    private String fromMail;

    // ========= Template related fields =========
    /**
     * Template number
     *
     * Association {@link MailTemplateDO#getId()}
     */
    private Long templateId;
    /**
     * Template code
     *
     * Redundant {@link MailTemplateDO#getCode()}
     */
    private String templateCode;
    /**
     * Template sender name
     *
     * Redundant {@link MailTemplateDO#getNickname()}
     */
    private String templateNickname;
    /**
     * Template title
     */
    private String templateTitle;
    /**
     * Template content
     *
     * Based on {@link MailTemplateDO#getContent()} Formatted content
     */
    private String templateContent;
    /**
     * Template parameters
     *
     * Based on {@link MailTemplateDO#getParams()} Parameters after input
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;

    // ========= Send related fields =========
    /**
     * Send status
     *
     * Enumeration {@link MailSendStatusEnum}
     */
    private Integer sendStatus;
    /**
     * Send time
     */
    private LocalDateTime sendTime;
    /**
     * Send return message ID
     */
    private String sendMessageId;
    /**
     * Sending exception
     */
    private String sendException;

}
