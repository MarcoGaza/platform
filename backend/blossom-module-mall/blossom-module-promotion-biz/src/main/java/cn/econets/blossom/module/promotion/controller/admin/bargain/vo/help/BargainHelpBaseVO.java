package cn.econets.blossom.module.promotion.controller.admin.bargain.vo.help;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Bargaining assistance Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class BargainHelpBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "5402")
    private Long userId;

    @Schema(description = "Bargaining activity name", requiredMode = Schema.RequiredMode.REQUIRED, example = "16825")
    private Long activityId;

    @Schema(description = "Bargaining record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1800")
    private Long recordId;

    @Schema(description = "Reduce bargaining，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "32300")
    private Integer reducePrice;

}
