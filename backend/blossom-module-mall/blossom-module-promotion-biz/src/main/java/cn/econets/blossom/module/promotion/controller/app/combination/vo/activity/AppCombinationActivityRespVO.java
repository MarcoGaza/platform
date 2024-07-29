package cn.econets.blossom.module.promotion.controller.app.combination.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Group buying activity Response VO")
@Data
public class AppCombinationActivityRespVO {

    @Schema(description = "Group buying activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Group buying activity name", requiredMode = Schema.RequiredMode.REQUIRED, example = "618 Group buy")
    private String name;

    @Schema(description = "Number of people in the group", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Integer userSize;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long spuId;

    @Schema(description = "Product image", requiredMode = Schema.RequiredMode.REQUIRED, example = "4096")
    // From SPU of picUrl Read
    private String picUrl;

    @Schema(description = "Commodity market price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    // From SPU of marketPrice Read
    private Integer marketPrice;

    @Schema(description = "Group purchase amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer combinationPrice;

}
