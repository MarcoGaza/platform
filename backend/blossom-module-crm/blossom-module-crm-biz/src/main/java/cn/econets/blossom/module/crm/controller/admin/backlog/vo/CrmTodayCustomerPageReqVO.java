package cn.econets.blossom.module.crm.controller.admin.backlog.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Need to contact the customer today Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmTodayCustomerPageReqVO extends PageParam {

    /**
     * Contact Status - Need to contact today
     */
    public static final int CONTACT_TODAY = 1;
    /**
     * Contact Status - Overdue
     */
    public static final int CONTACT_EXPIRED = 2;
    /**
     * Contact Status - Contacted
     */
    public static final int CONTACT_ALREADY = 3;

    @Schema(description = "Contact Status", example = "1")
    private Integer contactStatus;

    @Schema(description = "Scene Type", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType;

}
