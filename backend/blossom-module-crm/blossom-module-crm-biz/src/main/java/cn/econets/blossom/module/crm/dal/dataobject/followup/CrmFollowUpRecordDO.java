package cn.econets.blossom.module.crm.dal.dataobject.followup;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.framework.mybatis.core.type.StringListTypeHandler;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.enums.DictTypeConstants;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Follow-up records DO
 *
 * Used to record customers、Every follow-up of the contact
 *
 */
@TableName(value = "crm_follow_up_record", autoResultMap = true)
@KeySequence("crm_follow_up_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmFollowUpRecordDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * Data type
     *
     * Enumeration {@link CrmBizTypeEnum}
     */
    private Integer bizType;
    /**
     * Data number
     *
     * Relationship {@link CrmBizTypeEnum} Corresponding module DO of id Field
     */
    private Long bizId;

    /**
     * Follow-up type
     *
     * Relationship {@link DictTypeConstants#CRM_FOLLOW_UP_TYPE} Dictionary
     */
    private Integer type;
    /**
     * Follow-up content
     */
    private String content;
    /**
     * Next contact time
     */
    private LocalDateTime nextTime;

    /**
     * Picture
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> picUrls;
    /**
     * Attachment
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> fileUrls;

    /**
     * Array of associated opportunity numbers
     *
     * Relationship {@link CrmBusinessDO#getId()}
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> businessIds;
    /**
     * Array of associated contact numbers
     *
     * Relationship {@link CrmContactDO#getId()}
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> contactIds;


}
