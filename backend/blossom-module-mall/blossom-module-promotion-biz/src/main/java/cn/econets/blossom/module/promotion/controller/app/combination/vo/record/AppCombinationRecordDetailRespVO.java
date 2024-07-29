package cn.econets.blossom.module.promotion.controller.app.combination.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Group buying record details Response VO")
@Data
public class AppCombinationRecordDetailRespVO {

    @Schema(description = "Group leader's group record", requiredMode = Schema.RequiredMode.REQUIRED)
    private AppCombinationRecordRespVO headRecord;

    @Schema(description = "Members' group buying records", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AppCombinationRecordRespVO> memberRecords;

    @Schema(description = "The order number corresponding to the current user's group record", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long orderId;

}
