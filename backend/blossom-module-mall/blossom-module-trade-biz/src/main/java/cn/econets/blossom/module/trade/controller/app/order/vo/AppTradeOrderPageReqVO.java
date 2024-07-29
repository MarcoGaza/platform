package cn.econets.blossom.module.trade.controller.app.order.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Transaction order paging Request VO")
@Data
public class AppTradeOrderPageReqVO extends PageParam {

    @Schema(description = "Order Status", example = "1")
    @InEnum(value = TradeOrderStatusEnum.class, message = "Order status must be {value}")
    private Integer status;

    @Schema(description = "Do you want to comment?", example = "true")
    private Boolean commentStatus;

}
