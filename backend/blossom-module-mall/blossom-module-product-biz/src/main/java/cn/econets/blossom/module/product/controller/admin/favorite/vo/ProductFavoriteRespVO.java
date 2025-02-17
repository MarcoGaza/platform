package cn.econets.blossom.module.product.controller.admin.favorite.vo;

import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "Management Backend - Product Collection Response VO")
@Data
@ToString(callSuper = true)
public class ProductFavoriteRespVO  extends ProductSpuRespVO {

    @Schema(description = "userId", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    private Long userId;

    @Schema(description = "spuId", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    private Long spuId;

}
