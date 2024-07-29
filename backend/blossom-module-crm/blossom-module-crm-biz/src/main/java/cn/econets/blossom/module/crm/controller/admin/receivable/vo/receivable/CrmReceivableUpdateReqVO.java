package cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "Management Backend - CRM Payment Update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivableUpdateReqVO extends CrmReceivableBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25787")
    @NotNull(message = "IDCannot be empty")
    private Long id;

}
