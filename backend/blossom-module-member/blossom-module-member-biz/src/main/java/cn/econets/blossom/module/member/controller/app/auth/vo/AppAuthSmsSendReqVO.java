package cn.econets.blossom.module.member.controller.app.auth.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Schema(description = "User APP - Send mobile verification code Request VO")
@Data
@Accessors(chain = true)
public class AppAuthSmsSendReqVO {

    @Schema(description = "Mobile phone number", example = "15601691234")
    @Mobile
    private String mobile;

    @Schema(description = "Send scene,Corresponding SmsSceneEnum Enumeration", example = "1")
    @NotNull(message = "The sending scene cannot be empty")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
