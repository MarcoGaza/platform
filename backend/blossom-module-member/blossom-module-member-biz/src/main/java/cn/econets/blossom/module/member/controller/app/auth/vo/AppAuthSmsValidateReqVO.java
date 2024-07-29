package cn.econets.blossom.module.member.controller.app.auth.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(description = "User APP - Verify mobile phone verification code Request VO")
@Data
@Accessors(chain = true)
public class AppAuthSmsValidateReqVO {

    @Schema(description = "Mobile phone number", example = "15601691234")
    @Mobile
    private String mobile;

    @Schema(description = "Send scene,Corresponding SmsSceneEnum Enumeration", example = "1")
    @NotNull(message = "The sending scene cannot be empty")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

    @Schema(description = "Mobile verification code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "Mobile verification code cannot be empty")
    @Length(min = 4, max = 6, message = "The length of the mobile phone verification code is 4-6 position")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile verification code must be all numbers")
    private String code;

}
