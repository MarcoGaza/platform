package cn.econets.blossom.module.member.dal.dataobject.user;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.framework.ip.core.Area;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import cn.econets.blossom.module.member.dal.dataobject.group.MemberGroupDO;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelDO;
import cn.econets.blossom.module.system.enums.common.SexEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Member User DO
 *
 * uk_mobile Index：Based on {@link #mobile} Field
 *
 *
 */
@TableName(value = "member_user", autoResultMap = true)
@KeySequence("member_user_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUserDO extends TenantBaseDO {

    // ========== Account Information ==========

    /**
     * UserID
     */
    @TableId
    private Long id;
    /**
     * Mobile phone
     */
    private String mobile;
    /**
     * Encrypted password
     *
     * Because it is currently used {@link BCryptPasswordEncoder} Encryptor，So you don't need to handle it yourself salt Salt
     */
    private String password;
    /**
     * Account Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Register IP
     */
    private String registerIp;
    /**
     * Registration terminal
     * Enumeration {@link TerminalEnum}
     */
    private Integer registerTerminal;
    /**
     * Last loginIP
     */
    private String loginIp;
    /**
     * Last login time
     */
    private LocalDateTime loginDate;

    // ========== Basic Information ==========

    /**
     * User Nickname
     */
    private String nickname;
    /**
     * User avatar
     */
    private String avatar;

    /**
     * Real name
     */
    private String name;
    /**
     * Gender
     *
     * Enumeration {@link SexEnum}
     */
    private Integer sex;
    /**
     * Date of Birth
     */
    private LocalDateTime birthday;
    /**
     * Location
     *
     * Relationship {@link Area#getId()} Field
     */
    private Integer areaId;
    /**
     * User Notes
     */
    private String mark;

    // ========== Other information ==========

    /**
     * Points
     */
    private Integer point;
    // TODO Crazy：Add one totalPoint；Personal information interface needs to be returned

    /**
     * Member tag list，Separate by commas
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> tagIds;

    /**
     * Member Level Number
     *
     * Relationship {@link MemberLevelDO#getId()} Field
     */
    private Long levelId;
    /**
     * Membership Experience
     */
    private Integer experience;
    /**
     * User group number
     *
     * Relationship {@link MemberGroupDO#getId()} Field
     */
    private Long groupId;

}
