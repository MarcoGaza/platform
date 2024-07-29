package cn.econets.blossom.module.system.controller.admin.user.vo.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Update password in user profile Request VO")
@Data
public class UserProfileUpdatePasswordReqVO {

    @Schema(description = "Old password", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "The old password cannot be empty")
    @Length(min = 4, max = 16, message = "The password length is 4-16 position")
    private String oldPassword;

    @Schema(description = "New password", requiredMode = Schema.RequiredMode.REQUIRED, example = "654321")
    @NotEmpty(message = "The new password cannot be empty")
    @Length(min = 4, max = 16, message = "The password length is 4-16 position")
    private String newPassword;

}
