package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Member Analysis Response VO")
@Data
public class MemberAnalyseRespVO {

    @Schema(description = "Number of visitors", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer visitUserCount;

    @Schema(description = "Number of users placing orders", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer orderUserCount;

    @Schema(description = "Number of users who completed transactions", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer payUserCount;

    @Schema(description = "Average Order Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer atv;

    @Schema(description = "Comparison data", requiredMode = Schema.RequiredMode.REQUIRED)
    private DataComparisonRespVO<MemberAnalyseDataRespVO> comparison;

}
