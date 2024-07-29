package cn.econets.blossom.module.member.controller.app.auth.vo;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Schema(description = "User APP - Mobile phone + Verification code login Request VO,If you log in and bind a social user，Need to be passed social Parameters at the beginning")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthSmsLoginReqVO {

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Mobile
    private String mobile;

    @Schema(description = "Mobile verification code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "The mobile verification code cannot be empty")
    @Length(min = 4, max = 6, message = "The length of the mobile phone verification code is 4-6 position")
    @Pattern(regexp = "^[0-9]+$", message = "Mobile verification code must be all numbers")
    private String code;

    // ========== When binding social login，The following parameters need to be passed ==========

    @Schema(description = "Type of social platform，See SocialTypeEnum Enumeration value", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @Schema(description = "Authorization code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String socialCode;

    @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    private String socialState;

    @AssertTrue(message = "Authorization code cannot be empty")
    public boolean isSocialCodeValid() {
        return socialType == null || StrUtil.isNotEmpty(socialCode);
    }

    @AssertTrue(message = "Authorization state Cannot be empty")
    public boolean isSocialState() {
        return socialType == null || StrUtil.isNotEmpty(socialState);
    }

}
