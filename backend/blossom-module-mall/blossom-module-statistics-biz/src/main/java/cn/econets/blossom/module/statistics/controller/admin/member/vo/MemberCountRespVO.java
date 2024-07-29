package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Membership statistics Response VO")
@Data
public class MemberCountRespVO {

    @Schema(description = "User visits", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer visitUserCount;

    @Schema(description = "Number of registered users", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer registerUserCount;

}
