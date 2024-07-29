package cn.econets.blossom.module.crm.controller.admin.customer.vo.poolconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - CRM Customer High Seas Rules Response VO")
@Data
public class CrmCustomerPoolConfigRespVO {

    @Schema(description = "Whether to enable client high seas", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to enable the client's high seas cannot be empty")
    private Boolean enabled;

    @Schema(description = "Number of days released into the high seas without follow-up", example = "2")
    private Integer contactExpireDays;

    @Schema(description = "Number of days unsold items were placed on the high seas", example = "2")
    private Integer dealExpireDays;

    @Schema(description = "Whether to enable advance reminder", example = "true")
    private Boolean notifyEnabled;

    @Schema(description = "Days of advance reminder", example = "2")
    private Integer notifyDays;

}
