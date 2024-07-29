package cn.econets.blossom.module.member.controller.app.signin.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Sign-in rules Response VO")
@Data
public class AppMemberSignInConfigRespVO {

    @Schema(description = "Sign in x Sky", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    private Integer day;

    @Schema(description = "Reward points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer point;

}
