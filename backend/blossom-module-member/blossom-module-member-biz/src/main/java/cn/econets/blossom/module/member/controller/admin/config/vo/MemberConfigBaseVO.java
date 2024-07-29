package cn.econets.blossom.module.member.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Member Configuration Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberConfigBaseVO {

    @Schema(description = "Points deduction switch", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Point deduction development cannot be empty")
    private Boolean pointTradeDeductEnable;

    @Schema(description = "Points deduction，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "13506")
    @NotNull(message = "Points deduction cannot be empty")
    private Integer pointTradeDeductUnitPrice;

    @Schema(description = "Maximum Points Deduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "32428")
    @NotNull(message = "The maximum value of points deduction cannot be empty")
    private Integer pointTradeDeductMaxPrice;

    @Schema(description = "1 How many points are given for each yuan", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "1 The gift points cannot be empty")
    private Integer pointTradeGivePoint;

}
