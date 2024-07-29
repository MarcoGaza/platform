package cn.econets.blossom.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Email account simplification Response VO")
@Data
public class MailAccountSimpleRespVO {

    @Schema(description = "Mailbox Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Mailbox", requiredMode = Schema.RequiredMode.REQUIRED, example = "768541388@qq.com")
    private String mail;

}
