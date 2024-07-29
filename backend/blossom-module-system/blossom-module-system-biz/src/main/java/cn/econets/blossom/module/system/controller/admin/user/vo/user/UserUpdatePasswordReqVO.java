package cn.econets.blossom.module.system.controller.admin.user.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - User updates password Request VO")
@Data
public class UserUpdatePasswordReqVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "User ID cannot be empty")
    private Long id;

    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "The password cannot be empty")
    @Length(min = 4, max = 16, message = "The password length is 4-16 position")
    private String password;

}
