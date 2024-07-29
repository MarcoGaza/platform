package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Distribution User - Modify promoter Request VO")
@Data
@ToString(callSuper = true)
public class BrokerageUserUpdateBrokerageEnabledReqVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    @NotNull(message = "User ID cannot be empty")
    private Long id;

    @Schema(description = "Promotion Qualification", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Promotion qualification cannot be empty")
    private Boolean enabled;

}
