package cn.econets.blossom.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Simplification of email templates Response VO")
@Data
public class MailTemplateSimpleRespVO {

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Da Da Da")
    private String name;

}
