package cn.econets.blossom.module.system.dal.dataobject.social;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xingyuv.jushauth.config.AuthConfig;
import lombok.*;

/**
 * Social client DO
 *
 * Corresponding {@link AuthConfig} Configuration，Satisfy different tenants，Has its own client configuration，Achieve social interaction（Three parties）Login
 *
 */
@TableName(value = "system_social_client", autoResultMap = true)
@KeySequence("system_social_client_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialClientDO extends TenantBaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * Application name
     */
    private String name;
    /**
     * Social Type
     *
     * Enumeration {@link SocialTypeEnum}
     */
    private Integer socialType;
    /**
     * User Type
     *
     * Purpose：Different user types，Corresponding to different mini-programs，Requires own configuration
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * Client id
     */
    private String clientId;
    /**
     * Client Secret
     */
    private String clientSecret;

    /**
     * Agent Number
     *
     * Currently only part“Social Type”In use：
     * 1. Enterprise WeChat：Web application corresponding to the authorized party ID
     */
    private String agentId;

}
