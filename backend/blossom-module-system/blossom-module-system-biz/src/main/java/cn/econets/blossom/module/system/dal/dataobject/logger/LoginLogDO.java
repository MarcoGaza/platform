package cn.econets.blossom.module.system.dal.dataobject.logger;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.logger.LoginLogTypeEnum;
import cn.econets.blossom.module.system.enums.logger.LoginResultEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Login log table
 *
 * Attention，Includes two behaviors: login and logout
 *
 */
@TableName("system_login_log")
@KeySequence("system_login_log_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginLogDO extends BaseDO {

    /**
     * Log primary key
     */
    private Long id;
    /**
     * Log Type
     *
     * Enumeration {@link LoginLogTypeEnum}
     */
    private Integer logType;
    /**
     * Link tracking number
     */
    private String traceId;
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
     * User Account
     *
     * Redundant，Because the account can be changed
     */
    private String username;
    /**
     * Login result
     *
     * Enumeration {@link LoginResultEnum}
     */
    private Integer result;
    /**
     * User IP
     */
    private String userIp;
    /**
     * Browser UA
     */
    private String userAgent;

}
