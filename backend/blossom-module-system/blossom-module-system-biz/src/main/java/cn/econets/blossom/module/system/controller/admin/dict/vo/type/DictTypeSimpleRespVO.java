package cn.econets.blossom.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Dictionary type simplified information Response VO")
@Data
public class DictTypeSimpleRespVO {

    @Schema(description = "Dictionary type number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Dictionary type name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String name;

    @Schema(description = "Dictionary type", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_common_sex")
    private String type;

}
