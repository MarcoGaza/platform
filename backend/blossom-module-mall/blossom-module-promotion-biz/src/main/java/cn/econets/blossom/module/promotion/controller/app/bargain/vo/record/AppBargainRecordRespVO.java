package cn.econets.blossom.module.promotion.controller.app.bargain.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Bargaining record Response VO")
@Data
public class AppBargainRecordRespVO {

    @Schema(description = "Bargaining record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long spuId;
    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long skuId;

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22901")
    private Long activityId;

    @Schema(description = "Bargaining record status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Current Price", requiredMode = Schema.RequiredMode.REQUIRED, example = "102")
    private Integer bargainPrice;

    // ========== Activity related ==========

    @Schema(description = "Activity Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhao Liu")
    private String activityName;

    @Schema(description = "Event end time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    @Schema(description = "Product image", requiredMode = Schema.RequiredMode.REQUIRED,  // From SPU of picUrl Read
            example = "https://www.econets.cn/xx.png")
    private String picUrl;

    // ========== Order related ==========

    @Schema(description = "Order Number", example = "1024")
    private Long orderId;

    @Schema(description = "Payment Status", example = "true")
    private Boolean payStatus;

    @Schema(description = "Payment order number", example = "1024")
    private Long payOrderId;

}
