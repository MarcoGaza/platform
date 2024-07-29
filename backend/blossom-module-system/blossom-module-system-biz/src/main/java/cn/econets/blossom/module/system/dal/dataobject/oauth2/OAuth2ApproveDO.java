package cn.econets.blossom.module.system.dal.dataobject.oauth2;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * OAuth2 Approved DO
 *
 * User in sso.vue Interface，Record accepted scope List
 *
 */
@TableName(value = "system_oauth2_approve", autoResultMap = true)
@KeySequence("system_oauth2_approve_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2ApproveDO extends BaseDO {

    /**
     * Number，Database auto-increment
     */
    @TableId
    private Long id;
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
    private String scope;
    /**
     * Do you accept?
     *
     * true - Accept
     * false - Reject
     */
    private Boolean approved;
    /**
     * Expiration time
     */
    private LocalDateTime expiresTime;

}
