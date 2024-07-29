package cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Second-sale event Response VO")
@Data
public class AppSeckillActivityRespVO {

    @Schema(description = "Second sale activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Name of flash sale event", requiredMode = Schema.RequiredMode.REQUIRED, example = "Limited time sale at 9pm")
    private String name;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long spuId;

    @Schema(description = "Product images", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of picUrl Read
            example = "https://www.econets.cn/xx.png")
    private String picUrl;

    @Schema(description = "Commodity market price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of marketPrice Read
            example = "50")
    private Integer marketPrice;

    @Schema(description = "Second-sale activity status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Second kill inventory（Remaining）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer stock;
    @Schema(description = "Second kill inventory（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer totalStock;

    @Schema(description = "Second kill amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer seckillPrice;

}
