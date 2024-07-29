package cn.econets.blossom.module.trade.controller.app.order.vo.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "User App - Product review creation Request VO")
@Data
public class AppTradeOrderItemCommentCreateReqVO {

    @Schema(description = "Is it anonymous?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether anonymous cannot be empty")
    private Boolean anonymous;

    @Schema(description = "Transaction order item number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2312312")
    @NotNull(message = "Transaction order item number cannot be empty")
    private Long orderItemId;

    @Schema(description = "Description star rating 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "Description star rating 1-5 The score cannot be empty")
    private Integer descriptionScores;

    @Schema(description = "Service Stars 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "Service Stars 1-5 The score cannot be empty")
    private Integer benefitScores;

    @Schema(description = "Comment content", requiredMode = Schema.RequiredMode.REQUIRED, example = "It looks very beautiful when worn(*^▽^*)")
    @NotNull(message = "Comment content cannot be empty")
    private String content;

    @Schema(description = "Comment picture address array，Upload maximum comma separated 9 Zhang", requiredMode = Schema.RequiredMode.REQUIRED, example = "[https://www.econets.cn/xx.png]")
    @Size(max = 9, message = "The length of the comment image address array cannot exceed 9 Zhang")
    private List<String> picUrls;

}
