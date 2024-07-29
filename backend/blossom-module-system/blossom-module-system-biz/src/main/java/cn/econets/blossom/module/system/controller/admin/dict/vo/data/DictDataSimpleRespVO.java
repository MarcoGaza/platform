package cn.econets.blossom.module.system.controller.admin.dict.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Data dictionary simplification Response VO")
@Data
public class DictDataSimpleRespVO {

    @Schema(description = "Dictionary type", requiredMode = Schema.RequiredMode.REQUIRED, example = "gender")
    private String dictType;

    @Schema(description = "Dictionary key value", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String value;

    @Schema(description = "Dictionary tags", requiredMode = Schema.RequiredMode.REQUIRED, example = "Male")
    private String label;

    @Schema(description = "Color type，default、primary、success、info、warning、danger", example = "default")
    private String colorType;

    @Schema(description = "css Style", example = "btn-visible")
    private String cssClass;

}
