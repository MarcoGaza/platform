package cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "Management Backend - CRM Payment collection plan update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivablePlanUpdateReqVO extends CrmReceivablePlanBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25153")
    @NotNull(message = "IDCannot be empty")
    private Long id;

}
