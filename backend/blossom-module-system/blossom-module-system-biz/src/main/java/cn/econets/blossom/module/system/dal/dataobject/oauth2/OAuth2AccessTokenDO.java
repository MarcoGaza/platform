package cn.econets.blossom.module.system.dal.dataobject.oauth2;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OAuth2 Access Token DO
 *
 * The following fields，Not in use yet，Not supported yet：
 * user_name、authentication（User Information）
 *
 */
@TableName(value = "system_oauth2_access_token", autoResultMap = true)
@KeySequence("system_oauth2_access_token_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2AccessTokenDO extends TenantBaseDO {

    /**
     * Number，Database increment
     */
    @TableId
    private Long id;
    /**
     * Access Token
     */
    private String accessToken;
    /**
     * Refresh Token
     */
    private String refreshToken;
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
     * Relationship {@link OAuth2ClientDO#getId()}
     */
    private String clientId;
    /**
     * Authorization scope
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;
    /**
     * Expiration time
     */
    private LocalDateTime expiresTime;

}
