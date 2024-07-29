package cn.econets.blossom.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Last mail shift Response VO")
@Data
public class MailTemplateRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test name")
    private String name;

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String code;

    @Schema(description = "The email account number to send the message to", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long accountId;

    @Schema(description = "Sender's name", example = "Taro")
    private String nickname;

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Registration successful")
    private String title;

    @Schema(description = "Content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hello，Registration successful")
    private String content;

    @Schema(description = "Parameter array", example = "name,code")
    private List<String> params;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Remarks", example = "Ultraman")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
