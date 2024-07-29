package cn.econets.blossom.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Menu simplified information Response VO")
@Data
public class MenuSimpleRespVO {

    @Schema(description = "Menu number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Menu name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String name;

    @Schema(description = "Parent Menu ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long parentId;

    @Schema(description = "Type，See MenuTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

}
