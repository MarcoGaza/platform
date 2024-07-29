package cn.econets.blossom.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Email account creation/Modify Request VO")
@Data
public class MailAccountSaveReqVO {

    @Schema(description = "Number", example = "1024")
    private Long id;

    @Schema(description = "Mailbox", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
    @NotNull(message = "The mailbox cannot be empty")
    @Email(message = "Must be Email Format")
    private String mail;

    @Schema(description = "Username", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @NotNull(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotNull(message = "Password required")
    private String password;

    @Schema(description = "SMTP Server domain name", requiredMode = Schema.RequiredMode.REQUIRED, example = "www.econets.cn")
    @NotNull(message = "SMTP Server domain name cannot be empty")
    private String host;

    @Schema(description = "SMTP Server port", requiredMode = Schema.RequiredMode.REQUIRED, example = "80")
    @NotNull(message = "SMTP Server port cannot be empty")
    private Integer port;

    @Schema(description = "Is it enabled? ssl", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Is it enabled? ssl Required")
    private Boolean sslEnable;

}
