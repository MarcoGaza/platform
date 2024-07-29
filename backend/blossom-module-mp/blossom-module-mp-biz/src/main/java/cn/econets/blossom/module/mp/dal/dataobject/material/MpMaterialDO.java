package cn.econets.blossom.module.mp.dal.dataobject.material;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.chanjar.weixin.common.api.WxConsts;

/**
 * Public account material DO
 *
 * 1. <a href="https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/New_temporary_materials.html">Temporary material</a>
 * 2. <a href="https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/Adding_Permanent_Assets.html">Permanent material</a>
 *
 *
 */
@TableName("mp_material")
@KeySequence("mp_material_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpMaterialDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
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

    /**
     * Public account material id
     */
    private String mediaId;
    /**
     * File Type
     *
     * Enumeration {@link WxConsts.MediaFileType}
     */
    private String type;
    /**
     * Is it permanent?
     *
     * true - Permanent material
     * false - Temporary material
     */
    private Boolean permanent;
    /**
     * File server URL
     */
    private String url;

    /**
     * Name
     *
     * Permanent material：Not empty
     * Temporary material：May be empty。
     *      1. Empty：Pictures sent by fans、Voice, etc.
     *      2. Non-empty case：Pictures sent to fans proactively、Voice, etc.
     */
    private String name;

    /**
     * Public account files URL
     *
     * Only【Permanent material】Use
     */
    private String mpUrl;

    /**
     * Title of the video material
     *
     * Only【Permanent material】Use
     */
    private String title;
    /**
     * Description of video material
     *
     * Only【Permanent material】Use
     */
    private String introduction;

}
