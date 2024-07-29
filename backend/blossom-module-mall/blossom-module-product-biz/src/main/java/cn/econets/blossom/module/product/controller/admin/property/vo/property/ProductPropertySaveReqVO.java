package cn.econets.blossom.module.product.controller.admin.property.vo.property;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "Management Backend - Added new attribute items/Update Request VO")
@Data
public class ProductPropertySaveReqVO {

    @Schema(description = "Primary key", example = "1")
    private Long id;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Color")
    @NotBlank(message = "The name cannot be empty")
    private String name;

    @Schema(description = "Remarks", example = "Color")
    private String remark;

}
