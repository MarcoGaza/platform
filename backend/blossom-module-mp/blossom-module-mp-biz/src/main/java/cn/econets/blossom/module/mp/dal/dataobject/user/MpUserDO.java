package cn.econets.blossom.module.mp.dal.dataobject.user;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.tag.MpTagDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * WeChat public account fans DO
 *
 *
 */
@TableName(value = "mp_user", autoResultMap = true)
@KeySequence("mp_user_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpUserDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Fan logo
     */
    private String openid;
    /**
     * Follow Status
     *
     * Enumeration {@link CommonStatusEnum}
     * 1. Open - Already concerned
     * 2. Disable - Unfollow
     */
    private Integer subscribeStatus;
    /**
     * Pay attention to time
     */
    private LocalDateTime subscribeTime;
    /**
     * Unfollow time
     */
    private LocalDateTime unsubscribeTime;
    /**
     * Nickname
     *
     * Attention，2021-12-27 The public account interface no longer returns avatars and nicknames，Can only be obtained by logging in through the WeChat public account webpage
     */
    private String nickname;
    /**
     * Avatar address
     *
     * Attention，2021-12-27 The public account interface no longer returns avatars and nicknames，Can only be obtained by logging in through the WeChat public account webpage
     */
    private String headImageUrl;
    /**
     * Language
     */
    private String language;
    /**
     * Country
     */
    private String country;
    /**
     * Province
     */
    private String province;
    /**
     * City
     */
    private String city;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Tag number array
     *
     * Attention，Corresponding to {@link MpTagDO#getTagId()} Field
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> tagIds;

    /**
     * The public account number
     *
     * Relationship {@link MpAccountDO#getId()}
     */
    private Long accountId;
    /**
     * Public Account appId
     *
     * Redundant {@link MpAccountDO#getAppId()}
     */
    private String appId;

}
