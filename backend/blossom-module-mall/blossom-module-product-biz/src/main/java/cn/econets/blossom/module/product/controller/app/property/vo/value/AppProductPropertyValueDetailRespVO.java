package cn.econets.blossom.module.product.controller.app.property.vo.value;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Details of product attribute values Response VO")
@Data
public class AppProductPropertyValueDetailRespVO {

    @Schema(description = "Property number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long propertyId;

    @Schema(description = "Name of the property", requiredMode = Schema.RequiredMode.REQUIRED, example = "Color")
    private String propertyName;

    @Schema(description = "The number of the property value", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long valueId;

    @Schema(description = "Name of the property value", requiredMode = Schema.RequiredMode.REQUIRED, example = "Red")
    private String valueName;

}
