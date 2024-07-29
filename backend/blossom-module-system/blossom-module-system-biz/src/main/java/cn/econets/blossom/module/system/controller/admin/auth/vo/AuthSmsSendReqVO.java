package cn.econets.blossom.module.system.controller.admin.auth.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Send mobile verification code Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSmsSendReqVO {

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "testMobile")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Mobile
    private String mobile;

    @Schema(description = "SMS scenario", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The sending scene cannot be empty")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
