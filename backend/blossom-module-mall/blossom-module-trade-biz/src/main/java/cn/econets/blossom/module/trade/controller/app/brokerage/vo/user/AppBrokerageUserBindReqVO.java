package cn.econets.blossom.module.trade.controller.app.brokerage.vo.user;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Application App - Bind promoter Request VO")
@Data
public class AppBrokerageUserBindReqVO extends PageParam {

    @Schema(description = "Promoter Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The promoter number cannot be empty")
    private Long bindUserId;

}
