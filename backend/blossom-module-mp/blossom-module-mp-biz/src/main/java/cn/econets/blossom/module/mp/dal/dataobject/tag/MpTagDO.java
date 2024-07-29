package cn.econets.blossom.module.mp.dal.dataobject.tag;

import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import lombok.*;

import com.baomidou.mybatisplus.annotation.*;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;

/**
 * Public account tag DO
 *
 *
 */
@TableName("mp_tag")
@KeySequence("mp_tag_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpTagDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     * Public account tag id
     */
    private Long tagId;
    /**
     * Tag name
     */
    private String name;
    /**
     * Number of fans under this tag
     *
     * Redundant：{@link WxUserTag#getCount()} Field，Administrator click required【Synchronize】After，Update this field
     */
    private Integer count;

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
