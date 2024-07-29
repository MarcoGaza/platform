package cn.econets.blossom.module.promotion.controller.app.combination.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - A brief summary of the group purchase record Response VO")
@Data
public class AppCombinationRecordSummaryRespVO {

    /**
     * Load {@link #avatars} Number of
     */
    public static final Integer AVATAR_COUNT = 7;

    @Schema(description = "Number of group buying users", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userCount;

    @Schema(description = "Group buying user avatar list", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> avatars;

}
