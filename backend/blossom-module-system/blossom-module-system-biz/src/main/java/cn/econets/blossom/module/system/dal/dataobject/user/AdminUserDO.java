package cn.econets.blossom.module.system.dal.dataobject.user;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.type.JsonLongSetTypeHandler;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import cn.econets.blossom.module.system.enums.common.SexEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Administrative backend users DO
 *
 */
@TableName(value = "system_users", autoResultMap = true) // Because SQL Server of system_user is a keyword，So use system_users
@KeySequence("system_user_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDO extends TenantBaseDO {

    /**
     * UserID
     */
    @TableId
    private Long id;
    /**
     * User Account
     */
    private String username;
    /**
     * Encrypted password
     *
     * Because it is currently used {@link BCryptPasswordEncoder} Encryptor，So you don't need to handle it yourself salt Salt
     */
    private String password;
    /**
     * User Nickname
     */
    private String nickname;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Department ID
     */
    private Long deptId;
    /**
     * Position number array
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> postIds;
    /**
     * User mailbox
     */
    private String email;
    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * User gender
     *
     * Enumeration class {@link SexEnum}
     */
    private Integer sex;
    /**
     * User avatar
     */
    private String avatar;
    /**
     * Account Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Last loginIP
     */
    private String loginIp;
    /**
     * Last login time
     */
    private LocalDateTime loginDate;

}
