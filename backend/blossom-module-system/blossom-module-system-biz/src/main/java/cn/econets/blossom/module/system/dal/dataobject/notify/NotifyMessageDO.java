package cn.econets.blossom.module.system.dal.dataobject.notify;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Internal message DO
 *
 */
@TableName(value = "system_notify_message", autoResultMap = true)
@KeySequence("system_notify_message_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyMessageDO extends BaseDO {

    /**
     * Internal message number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Field、Or AdminUserDO of id Field
     */
    private Long userId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;

    // ========= Template related fields =========

    /**
     * Template number
     *
     * Relationship {@link NotifyTemplateDO#getId()}
     */
    private Long templateId;
    /**
     * Template code
     *
     * Relationship {@link NotifyTemplateDO#getCode()}
     */
    private String templateCode;
    /**
     * Template type
     *
     * Redundant {@link NotifyTemplateDO#getType()}
     */
    private Integer templateType;
    /**
     * Template sender name
     *
     * Redundant {@link NotifyTemplateDO#getNickname()}
     */
    private String templateNickname;
    /**
     * Template content
     *
     * Based on {@link NotifyTemplateDO#getContent()} Formatted content
     */
    private String templateContent;
    /**
     * Template parameters
     *
     * Based on {@link NotifyTemplateDO#getParams()} Parameters after input
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;

    // ========= Read related fields =========

    /**
     * Has it been read?
     */
    private Boolean readStatus;
    /**
     * Reading time
     */
    private LocalDateTime readTime;

}
