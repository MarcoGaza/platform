package cn.econets.blossom.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Email template creation/Modify Request VO")
@Data
public class MailTemplateSaveReqVO {

    @Schema(description = "Number", example = "1024")
    private Long id;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test name")
    @NotNull(message = "The name cannot be empty")
    private String name;

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @NotNull(message = "Template number cannot be empty")
    private String code;

    @Schema(description = "The email account number to send the message to", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The email account number cannot be empty")
    private Long accountId;

    @Schema(description = "Sender's name", example = "Taro")
    private String nickname;

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Registration successful")
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @Schema(description = "Content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hello，Registration successful")
    @NotEmpty(message = "Content cannot be empty")
    private String content;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Remarks", example = "Ultraman")
    private String remark;

}
