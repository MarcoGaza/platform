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

@Schema(description = "User APP - Modify mobile phone Request VO")
@Data
public class AppMemberUserUpdateMobileReqVO {

    @Schema(description = "Mobile verification code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "The mobile verification code cannot be empty")
    @Length(min = 4, max = 6, message = "The length of the mobile verification code is 4-6 position")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile verification code must be all numbers")
    private String code;

    @Schema(description = "Mobile phone number",requiredMode = Schema.RequiredMode.REQUIRED, example = "15823654487")
    @NotBlank(message = "Mobile number cannot be empty")
    @Length(min = 8, max = 11, message = "The length of the mobile phone number is 8-11 position")
    @Mobile
    private String mobile;

    @Schema(description = "Original mobile verification code", example = "1024")
    @Length(min = 4, max = 6, message = "The length of the mobile verification code is 4-6 position")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile verification code must be all numbers")
    private String oldCode;

}
