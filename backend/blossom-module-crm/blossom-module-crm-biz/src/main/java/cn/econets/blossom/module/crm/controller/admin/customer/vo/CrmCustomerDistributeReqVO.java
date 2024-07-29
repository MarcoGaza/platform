package cn.econets.blossom.module.crm.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - CRM The customer allocates the high seas to the corresponding person in charge Request VO")
@Data
public class CrmCustomerDistributeReqVO {

    @Schema(description = "Customer Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1024]")
    @NotEmpty(message = "Customer number cannot be empty")
    private List<Long> ids;

    @Schema(description = "Person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The person in charge cannot be empty")
    private Long ownerUserId;

}
