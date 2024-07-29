package cn.econets.blossom.module.system.controller.admin.notify.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Internal message template Response VO")
@Data
public class NotifyTemplateRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test template")
    private String name;

    @Schema(description = "Template code", requiredMode = Schema.RequiredMode.REQUIRED, example = "SEND_TEST")
    private String code;

    @Schema(description = "Template type，Corresponding system_notify_template_type Dictionary", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "Sender's name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    private String nickname;

    @Schema(description = "Template content", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am the template content")
    private String content;

    @Schema(description = "Parameter array", example = "name,code")
    private List<String> params;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
