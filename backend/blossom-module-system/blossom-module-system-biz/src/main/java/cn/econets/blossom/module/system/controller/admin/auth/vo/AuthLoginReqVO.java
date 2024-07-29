package cn.econets.blossom.module.system.controller.admin.auth.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Schema(description = "Management Backend - Login with username and password Request VO，If you log in and bind a social user，Need to be transferred social Parameters at the beginning")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginReqVO {

    @Schema(description = "Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossomyuanma")
    @NotEmpty(message = "The login account cannot be empty")
    @Length(min = 4, max = 16, message = "Account length is 4-16 position")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "The account format is numbers and letters")
    private String username;

    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "buzhidao")
    @NotEmpty(message = "The password cannot be empty")
    @Length(min = 4, max = 16, message = "The password length is 4-16 position")
    private String password;

    // ========== Image verification code related ==========

    @Schema(description = "Verification code，When verification code is turned on，Need to be transferred", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "PfcH6mgr8tpXuMWFjvW6YVaqrswIuwmWI5dsVZSg7sGpWtDCUbHuDEXl3cFB1+VvCC/rAkSwK8Fad52FSuncVg==")
    @NotEmpty(message = "Verification code cannot be empty", groups = CodeEnableGroup.class)
    private String captchaVerification;

    // ========== When binding social login，The following parameters need to be passed ==========

    @Schema(description = "Type of social platform，See SocialTypeEnum Enumeration value", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @Schema(description = "Authorization code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String socialCode;

    @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    private String socialState;

    /**
     * Enable verification code Group
     */
    public interface CodeEnableGroup {}

    @AssertTrue(message = "Authorization code cannot be empty")
    public boolean isSocialCodeValid() {
        return socialType == null || StrUtil.isNotEmpty(socialCode);
    }

    @AssertTrue(message = "Authorization state Cannot be empty")
    public boolean isSocialState() {
        return socialType == null || StrUtil.isNotEmpty(socialState);
    }

}
