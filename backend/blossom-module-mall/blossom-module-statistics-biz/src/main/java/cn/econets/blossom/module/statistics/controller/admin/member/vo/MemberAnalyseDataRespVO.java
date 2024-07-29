package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Member analysis data Response VO")
@Data
public class MemberAnalyseDataRespVO {

    @Schema(description = "Number of members", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer registerUserCount;

    @Schema(description = "Number of active users", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer visitUserCount;

    @Schema(description = "Number of top-up members", requiredMode = Schema.RequiredMode.REQUIRED, example = "221")
    private Integer rechargeUserCount;

}
