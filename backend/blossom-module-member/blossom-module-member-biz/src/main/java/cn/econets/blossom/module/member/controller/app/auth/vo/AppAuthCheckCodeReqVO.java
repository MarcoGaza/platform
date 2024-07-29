package cn.econets.blossom.module.member.controller.app.auth.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

// TODO code review Related logic
@Schema(description = "User APP - Verify verification code Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthCheckCodeReqVO {

    @Schema(description = "Mobile phone number", example = "15601691234")
    @NotBlank(message = "Mobile number cannot be empty")
    @Mobile
    private String mobile;

    @Schema(description = "Mobile verification code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotBlank(message = "Mobile verification code cannot be empty")
    @Length(min = 4, max = 6, message = "The length of the mobile verification code is 4-6 position")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile verification code must be all numbers")
    private String code;

    @Schema(description = "Send scene,Corresponding SmsSceneEnum Enumeration", example = "1")
    @NotNull(message = "The sending scene cannot be empty")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
