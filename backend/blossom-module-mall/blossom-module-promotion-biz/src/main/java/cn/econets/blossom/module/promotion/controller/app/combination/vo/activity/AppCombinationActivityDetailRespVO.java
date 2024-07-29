package cn.econets.blossom.module.promotion.controller.app.combination.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "User App - Group buying activity details Response VO")
@Data
public class AppCombinationActivityDetailRespVO {

    @Schema(description = "Group buying activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Group buying activity name", requiredMode = Schema.RequiredMode.REQUIRED, example = "618 Group buy")
    private String name;

    @Schema(description = "Activity Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Activity start time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    @Schema(description = "Event end time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    @Schema(description = "Number of people in the group", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Integer userSize;

    @Schema(description = "Number of successful group purchases", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer successCount;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long spuId;

    @Schema(description = "Total purchase quantity limit", example = "10")
    private Integer totalLimitCount;

    @Schema(description = "Single purchase limit quantity", example = "5")
    private Integer singleLimitCount;

    @Schema(description = "Product information array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Product> products;

    @Schema(description = "Product Information")
    @Data
    public static class Product {

        @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4096")
        private Long skuId;

        @Schema(description = "Group purchase amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer combinationPrice;

    }

}
