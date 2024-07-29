package cn.econets.blossom.module.product.controller.admin.brand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Product brand update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductBrandUpdateReqVO extends ProductBrandBaseVO {

    @Schema(description = "Brand Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Brand number cannot be empty")
    private Long id;

}
