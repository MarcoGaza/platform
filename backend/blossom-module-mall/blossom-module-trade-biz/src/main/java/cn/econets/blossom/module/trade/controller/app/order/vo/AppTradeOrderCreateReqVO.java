package cn.econets.blossom.module.trade.controller.app.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;

@Schema(description = "User App - Trading order creation Request VO")
@Data
public class AppTradeOrderCreateReqVO extends AppTradeOrderSettlementReqVO {

    @Schema(description = "Remarks", example = "This is my order")
    private String remark;

    @AssertTrue(message = "The delivery method cannot be empty")
    @JsonIgnore
    public boolean isDeliveryTypeNotNull() {
        return getDeliveryType() != null;
    }

}
