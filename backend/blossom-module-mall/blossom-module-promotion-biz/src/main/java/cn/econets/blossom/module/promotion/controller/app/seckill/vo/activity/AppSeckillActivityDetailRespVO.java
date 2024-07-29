package cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "User App - Details of flash sale Response VO")
@Data
public class AppSeckillActivityDetailRespVO {

    @Schema(description = "Second sale activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Name of flash sale event", requiredMode = Schema.RequiredMode.REQUIRED, example = "Limited time sale at 9pm")
    private String name;

    @Schema(description = "Activity Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Activity start time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    @Schema(description = "Event end time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long spuId;

    @Schema(description = "Total purchase quantity", example = "10")
    private Integer totalLimitCount;

    @Schema(description = "Single purchase limit quantity", example = "5")
    private Integer singleLimitCount;

    @Schema(description = "Second kill inventory（Remaining）", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    private Integer stock;

    @Schema(description = "Second kill inventory（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer totalStock;

    @Schema(description = "Product information array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Product> products;

    @Schema(description = "Product Information")
    @Data
    public static class Product {

        @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4096")
        private Long skuId;

        @Schema(description = "Second kill amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer seckillPrice;

        @Schema(description = "Flash sale limited stock", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
        private Integer stock;

    }

}
