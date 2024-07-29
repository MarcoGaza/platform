package cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Application App - Distribution and withdrawal paging Request VO")
@Data
public class AppBrokerageWithdrawPageReqVO extends PageParam {

    @Schema(description = "Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(value = BrokerageWithdrawTypeEnum.class, message = "Type must be {value}")
    private Integer type;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(value = BrokerageWithdrawStatusEnum.class, message = "The status must be {value}")
    private Integer status;

}
