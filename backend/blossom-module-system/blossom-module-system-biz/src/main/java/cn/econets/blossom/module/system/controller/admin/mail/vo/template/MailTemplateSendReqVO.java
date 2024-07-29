package cn.econets.blossom.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "Management Backend - Email sent Req VO")
@Data
public class MailTemplateSendReqVO {

    @Schema(description = "Receive mailbox", requiredMode = Schema.RequiredMode.REQUIRED, example = "7685413@qq.com")
    @NotEmpty(message = "The receiving mailbox cannot be empty")
    private String mail;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @NotNull(message = "Template code cannot be empty")
    private String templateCode;

    @Schema(description = "Template parameters")
    private Map<String, Object> templateParams;

}
