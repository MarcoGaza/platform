package cn.econets.blossom.module.product.controller.admin.property.vo.value;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Product attribute value Response VO")
@Data
public class ProductPropertyValueRespVO {

    @Schema(description = "Primary key", example = "1024")
    private Long id;

    @Schema(description = "The number of the property item", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The attribute item number cannot be empty")
    private Long propertyId;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Red")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Schema(description = "Remarks", example = "Color")
    private String remark;

    @Schema(description = "Creation time")
    private LocalDateTime createTime;

}
