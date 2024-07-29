package cn.econets.blossom.module.member.controller.app.signin.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Sign-in record Response VO")
@Data
public class AppMemberSignInRecordRespVO {

    @Schema(description = "Sign in on which day", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer day;

    @Schema(description = "Sign-in score", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer point;

    @Schema(description = "Sign-in experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer experience;

    @Schema(description = "Sign-in time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
