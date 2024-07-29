package cn.econets.blossom.module.system.dal.dataobject.oauth2;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OAuth2 Authorization code DO
 *
 */
@TableName(value = "system_oauth2_code", autoResultMap = true)
@KeySequence("system_oauth2_code_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2CodeDO extends BaseDO {

    /**
     * Number，Database increment
     */
    private Long id;
    /**
     * Authorization code
     */
    private String code;
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
    /**
     * Client ID
     *
     * Relationship {@link OAuth2ClientDO#getClientId()}
     */
    private String clientId;
    /**
     * Authorization scope
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;
    /**
     * Redirect address
     */
    private String redirectUri;
    /**
     * Status
     */
    private String state;
    /**
     * Expiration time
     */
    private LocalDateTime expiresTime;

}
