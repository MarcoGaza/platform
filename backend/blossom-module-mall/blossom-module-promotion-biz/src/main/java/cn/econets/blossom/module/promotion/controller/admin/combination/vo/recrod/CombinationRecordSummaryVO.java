package cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Group buying record information statistics Response VO")
@Data
public class CombinationRecordSummaryVO {

    @Schema(description = "All group purchase records", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userCount;

    @Schema(description = "Group formation record", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long successCount;

    @Schema(description = "Virtual group record", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long virtualGroupCount;

}
