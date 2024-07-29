package cn.econets.blossom.module.crm.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - CRM Customer Lock/Unlock Request VO")
@Data
public class CrmCustomerLockReqVO {

    @Schema(description = "Customer Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    private Long id;

    @Schema(description = "Customer locked status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Boolean lockStatus;

}
