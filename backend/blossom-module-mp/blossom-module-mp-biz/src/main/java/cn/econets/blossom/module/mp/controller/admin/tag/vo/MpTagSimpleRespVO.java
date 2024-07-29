package cn.econets.blossom.module.mp.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Public account tag simplified information Response VO")
@Data
public class MpTagSimpleRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Tag number of the public account", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long tagId;

    @Schema(description = "Tag name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Happy")
    private String name;

}
