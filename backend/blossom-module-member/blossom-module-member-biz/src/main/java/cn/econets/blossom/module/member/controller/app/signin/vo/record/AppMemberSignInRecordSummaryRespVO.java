package cn.econets.blossom.module.member.controller.app.signin.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Personal check-in statistics Response VO")
@Data
public class AppMemberSignInRecordSummaryRespVO {

    @Schema(description = "Total number of check-in days", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer totalDay;

    @Schema(description = "Continuous sign-in x Sky", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Integer continuousDay;

    @Schema(description = "Have you signed in today?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean todaySignIn;

}
