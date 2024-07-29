package cn.econets.blossom.module.system.controller.admin.mail.vo.account;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Email account paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailAccountPageReqVO extends PageParam {

    @Schema(description = "Mailbox", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
    private String mail;

    @Schema(description = "Username" , requiredMode = Schema.RequiredMode.REQUIRED , example = "test")
    private String username;

}
