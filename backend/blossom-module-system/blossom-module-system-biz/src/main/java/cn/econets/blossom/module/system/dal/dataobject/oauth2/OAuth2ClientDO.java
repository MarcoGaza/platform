package cn.econets.blossom.module.system.dal.dataobject.oauth2;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.oauth2.OAuth2GrantTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * OAuth2 Client DO
 *
 */
@TableName(value = "system_oauth2_client", autoResultMap = true)
@KeySequence("system_oauth2_client_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2ClientDO extends BaseDO {

    /**
     * Number，Database auto-increment
     *
     * Because SQL Server In storage String There is a problem with the primary key，So use it temporarily Long Type
     */
    @TableId
    private Long id;
    /**
     * Client ID
     */
    private String clientId;
    /**
     * Client Key
     */
    private String secret;
    /**
     * Application name
     */
    private String name;
    /**
     * Application Icon
     */
    private String logo;
    /**
     * Application Description
     */
    private String description;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Access token validity period
     */
    private Integer accessTokenValiditySeconds;
    /**
     * Validity period of refresh token
     */
    private Integer refreshTokenValiditySeconds;
    /**
     * Redirectable URI Address
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> redirectUris;
    /**
     * Authorization type（Mode）
     *
     * Enumeration {@link OAuth2GrantTypeEnum}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> authorizedGrantTypes;
    /**
     * Authorization scope
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;
    /**
     * Automatically authorized Scope
     *
     * code When authorized，If scope Within this range，Automatically pass
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> autoApproveScopes;
    /**
     * Permissions
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> authorities;
    /**
     * Resources
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> resourceIds;
    /**
     * Additional information，JSON Format
     */
    private String additionalInformation;

}
