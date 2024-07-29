package cn.econets.blossom.module.system.controller.admin.notify.vo.template;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Creating a template for internal messages/Modify Request VO")
@Data
public class NotifyTemplateSaveReqVO {

    @Schema(description = "ID", example = "1024")
    private Long id;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test template")
    @NotEmpty(message = "Template name cannot be empty")
    private String name;

    @Schema(description = "Template code", requiredMode = Schema.RequiredMode.REQUIRED, example = "SEND_TEST")
    @NotNull(message = "Template code cannot be empty")
    private String code;

    @Schema(description = "Template type，Corresponding system_notify_template_type Dictionary", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Template type cannot be empty")
    private Integer type;

    @Schema(description = "Sender's name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    @NotEmpty(message = "The sender name cannot be empty")
    private String nickname;

    @Schema(description = "Template content", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am the template content")
    @NotEmpty(message = "Template content cannot be empty")
    private String content;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The status must be {value}")
    private Integer status;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

}
