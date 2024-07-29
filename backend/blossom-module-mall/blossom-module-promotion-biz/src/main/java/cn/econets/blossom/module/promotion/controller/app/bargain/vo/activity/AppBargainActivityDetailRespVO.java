package cn.econets.blossom.module.promotion.controller.app.bargain.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Details of bargaining activities Response VO")
@Data
public class AppBargainActivityDetailRespVO {

    @Schema(description = "Bargaining activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Bargaining activity name", requiredMode = Schema.RequiredMode.REQUIRED, example = "618 Big Bargain")
    private String name;

    @Schema(description = "Activity start time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    @Schema(description = "Event end time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long spuId;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long skuId;

    @Schema(description = "Product price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;

    @Schema(description = "Product Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "I want to eat tomatoes")
    private String description;

    @Schema(description = "Bargaining inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "512")
    private Integer stock;

    @Schema(description = "Product images", requiredMode = Schema.RequiredMode.REQUIRED, example = "4096") // From SPU of picUrl Read
    private String picUrl;

    @Schema(description = "Commodity market price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "50") // From SPU of marketPrice Read
    private Integer marketPrice;

    @Schema(description = "Bargaining starting price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer bargainFirstPrice;

    @Schema(description = "Minimum bargaining amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer bargainMinPrice;

    @Schema(description = "Number of successful bargaining", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer successUserCount;

}
