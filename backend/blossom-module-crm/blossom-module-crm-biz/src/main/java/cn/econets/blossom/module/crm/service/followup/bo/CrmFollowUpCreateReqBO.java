package cn.econets.blossom.module.crm.service.followup.bo;

import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.enums.DictTypeConstants;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Follow-up information Create Req BO
 *
 */
@Data
public class CrmFollowUpCreateReqBO {

    /**
     * Data type
     *
     * Enumeration {@link CrmBizTypeEnum}
     */
    @NotNull(message = "Data type cannot be empty")
    private Integer bizType;
    /**
     * Data number
     *
     * Relationship {@link CrmBizTypeEnum} Corresponding module DO of id Field
     */
    @NotNull(message = "Data number cannot be empty")
    private Long bizId;

    /**
     * Follow-up type
     *
     * Relationship {@link DictTypeConstants#CRM_FOLLOW_UP_TYPE} Dictionary
     */
    @NotNull(message = "Follow-up type cannot be empty")
    private Integer type;
    /**
     * Follow-up content
     */
    @NotEmpty(message = "Follow-up content cannot be empty")
    private String content;
    /**
     * Next contact time
     */
    @NotNull(message = "The next contact time cannot be empty")
    private LocalDateTime nextTime;

    /**
     * Picture
     */
    private List<String> picUrls;
    /**
     * Attachment
     */
    private List<String> fileUrls;

    /**
     * Array of associated opportunity numbers
     *
     * Relationship {@link CrmBusinessDO#getId()}
     */
    private List<Long> businessIds;

    /**
     * Array of associated contact numbers
     *
     * Relationship {@link CrmContactDO#getId()}
     */
    private List<Long> contactIds;

}
