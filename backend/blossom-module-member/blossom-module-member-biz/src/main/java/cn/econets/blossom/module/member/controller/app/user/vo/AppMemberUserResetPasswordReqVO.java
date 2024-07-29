package cn.econets.blossom.module.member.controller.app.user.vo;

import cn.econets.blossom.framework.common.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Schema(description = "User APP - Reset password Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppMemberUserResetPasswordReqVO {

    @Schema(description = "New password", requiredMode = Schema.RequiredMode.REQUIRED, example = "buzhidao")
    @NotEmpty(message = "The new password cannot be empty")
    @Length(min = 4, max = 16, message = "The password length is 4-16 position")
    private String password;

    @Schema(description = "Mobile verification code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "The mobile verification code cannot be empty")
    @Length(min = 4, max = 6, message = "The length of the mobile phone verification code is 4-6 position")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile verification code must be all numbers")
    private String code;

    @Schema(description = "Mobile phone number",requiredMode = Schema.RequiredMode.REQUIRED,example = "15878962356")
    @NotBlank(message = "Mobile number cannot be empty")
    @Mobile
    private String mobile;

}
