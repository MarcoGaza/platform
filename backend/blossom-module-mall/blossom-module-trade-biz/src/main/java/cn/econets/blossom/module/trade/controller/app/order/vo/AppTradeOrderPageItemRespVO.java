package cn.econets.blossom.module.trade.controller.app.order.vo;

import cn.econets.blossom.module.trade.controller.app.order.vo.item.AppTradeOrderItemRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "User App - Pagination items of order transactions Response VO")
@Data
public class AppTradeOrderPageItemRespVO {

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Order serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1146347329394184195")
    private String no;

    @Schema(description = "Order Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer type;

    @Schema(description = "Order Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Quantity of goods purchased", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer productCount;

    @Schema(description = "Do you want to comment?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean commentStatus;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== Price + Basic payment information ==========

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long payOrderId;

    @Schema(description = "Amount payable，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer payPrice;

    // ========== Receive + Basic logistics information ==========

    @Schema(description = "Delivery method", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer deliveryType;

    /**
     * Order item array
     */
    private List<AppTradeOrderItemRespVO> items;

}
