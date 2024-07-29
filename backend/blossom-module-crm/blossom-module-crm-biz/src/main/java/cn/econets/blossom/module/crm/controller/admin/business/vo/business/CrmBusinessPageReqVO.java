package cn.econets.blossom.module.crm.controller.admin.business.vo.business;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Business Opportunities Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmBusinessPageReqVO extends PageParam {

    @Schema(description = "Opportunity Name", example = "Li Si")
    private String name;

    @Schema(description = "Customer Number", example = "10795")
    private Long customerId;

    @Schema(description = "Contact Number", example = "10795")
    private Long contactId;

    @Schema(description = "Scene Type", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // Scene Type，for null When it means all

}
