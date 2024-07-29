package cn.econets.blossom.module.product.controller.admin.spu.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.product.enums.spu.ProductSpuStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Products SPU Status Update Request VO")
@Data
public class ProductSpuUpdateStatusReqVO{

    @Schema(description = "Product Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product number cannot be empty")
    private Long id;

    @Schema(description = "Product Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product status cannot be empty")
    @InEnum(ProductSpuStatusEnum.class)
    private Integer status;

}
