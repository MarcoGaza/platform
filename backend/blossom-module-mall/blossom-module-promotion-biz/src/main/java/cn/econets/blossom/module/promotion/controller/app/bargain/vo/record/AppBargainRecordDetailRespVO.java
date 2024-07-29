package cn.econets.blossom.module.promotion.controller.app.bargain.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Bargaining record details Response VO")
@Data
public class AppBargainRecordDetailRespVO {

    public static final int HELP_ACTION_NONE = 1; // Help cut action - Not helped cut，Can help cut
    public static final int HELP_ACTION_FULL = 2; // Help cut action - Not helped cut，Cannot help cut（The number of times you can help to chop has been reached)
    public static final int HELP_ACTION_SUCCESS = 3; // Help cut action - Have helped cut

    // ========== Bargaining Record ==========

    @Schema(description = "Bargaining record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    private Long userId;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long spuId;
    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long skuId;

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhao Liu")
    private Long activityId;

    @Schema(description = "Bargaining starting price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    private Integer bargainFirstPrice;

    @Schema(description = "Current bargaining price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    private Integer bargainPrice;

    @Schema(description = "Bargaining record status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    // ========== Order related ========== Attention：Only my own bargaining record，will return，Guaranteed privacy

    @Schema(description = "Order Number", example = "1024")
    private Long orderId;

    @Schema(description = "Payment Status", example = "true")
    private Boolean payStatus;

    @Schema(description = "Payment order number", example = "1024")
    private Long payOrderId;

    // ========== Assistance Record ==========

    private Integer helpAction;

}
