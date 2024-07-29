package cn.econets.blossom.module.crm.controller.admin.followup.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Follow-up record paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmFollowUpRecordPageReqVO extends PageParam {

    @Schema(description = "Data type", example = "2")
    private Integer bizType;

    @Schema(description = "Data number", example = "5564")
    private Long bizId;

}
