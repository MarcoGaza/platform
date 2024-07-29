package cn.econets.blossom.module.trade.controller.app.base.spu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Products SPU Basics Response VO
 *
 */
@Data
public class AppProductSpuBaseRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Products SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

    @Schema(description = "Product main image address", example = "https://www.econets.cn/xx.png")
    private String picUrl;

}
