package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Distribution User - Clear promoters Request VO")
@Data
@ToString(callSuper = true)
public class BrokerageUserClearBrokerageUserReqVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    @NotNull(message = "User ID cannot be empty")
    private Long id;

}
