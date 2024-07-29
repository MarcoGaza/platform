package cn.econets.blossom.module.promotion.controller.app.reward.vo;

import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Save a lot of money and get a free gift Response VO")
@Data
public class AppRewardActivityRespVO {

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer id;

    @Schema(description = "Activity Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Activity Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Manila Manila")
    private String name;

    @Schema(description = "Condition type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer conditionType;

    @Schema(description = "Product Range", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer productScope;

    @Schema(description = "Products SPU Numbered array", example = "1,2,3")
    private List<Long> productSpuIds;

    @Schema(description = "Array of preferential rules")
    private List<RewardActivityBaseVO.Rule> rules;

}
