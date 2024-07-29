package cn.econets.blossom.module.bpm.controller.admin.definition.vo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Streamlining of process forms Response VO")
@Data
public class BpmFormSimpleRespVO {

    @Schema(description = "Form number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Form Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

}
