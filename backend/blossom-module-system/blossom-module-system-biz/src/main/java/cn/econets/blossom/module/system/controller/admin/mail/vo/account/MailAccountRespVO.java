package cn.econets.blossom.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Email Account Response VO")
@Data
public class MailAccountRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Mailbox", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmial.com")
    private String mail;

    @Schema(description = "Username", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String username;

    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String password;

    @Schema(description = "SMTP Server domain name", requiredMode = Schema.RequiredMode.REQUIRED, example = "www.econets.cn")
    private String host;

    @Schema(description = "SMTP Server port", requiredMode = Schema.RequiredMode.REQUIRED, example = "80")
    private Integer port;

    @Schema(description = "Is it enabled? ssl", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean sslEnable;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
