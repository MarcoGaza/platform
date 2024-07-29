package cn.econets.blossom.module.system.controller.admin.auth.vo;

import cn.econets.blossom.framework.common.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Login via SMS verification code Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSmsLoginReqVO {

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossomyuanma")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Mobile
    private String mobile;

    @Schema(description = "SMS verification code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "Verification code cannot be empty")
    private String code;

}
