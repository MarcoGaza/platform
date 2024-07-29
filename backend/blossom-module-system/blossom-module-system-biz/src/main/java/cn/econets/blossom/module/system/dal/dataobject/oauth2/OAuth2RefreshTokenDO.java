package cn.econets.blossom.module.system.dal.dataobject.oauth2;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OAuth2 Refresh Token
 *
 */
@TableName(value = "system_oauth2_refresh_token", autoResultMap = true)
// Because Oracle of SEQ The name length is limited，So use it first system_oauth2_access_token_seq Bar，It's no problem anyway
@KeySequence("system_oauth2_access_token_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OAuth2RefreshTokenDO extends BaseDO {

    /**
     * Number，Database dictionary
     */
    private Long id;
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
