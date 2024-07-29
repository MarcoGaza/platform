package cn.econets.blossom.module.product.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Management Backend - Product review creation Request VO")
@Data
public class ProductCommentCreateReqVO {

    @Schema(description = "Evaluator", requiredMode = Schema.RequiredMode.REQUIRED, example = "16868")
    private Long userId;

    @Schema(description = "Evaluation line item", requiredMode = Schema.RequiredMode.REQUIRED, example = "19292")
    private Long orderItemId;

    @Schema(description = "Name of the reviewer", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little girl")
    @NotNull(message = "The name of the reviewer cannot be empty")
    private String userNickname;

    @Schema(description = "Evaluator's avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    @NotNull(message = "The reviewer's avatar cannot be empty")
    private String userAvatar;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;

    @Schema(description = "Description star rating 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "The description star rating cannot be empty")
    private Integer descriptionScores;

    @Schema(description = "Service Stars 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "Service star rating cannot be empty")
    private Integer benefitScores;

    @Schema(description = "Comment content", requiredMode = Schema.RequiredMode.REQUIRED, example = "It feels very smooth and cool to wear")
    @NotNull(message = "Comment content cannot be empty")
    private String content;

    @Schema(description = "Comment picture address array，Upload at most separated by commas 9 Zhang", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "[https://www.econets.cn/xx.png]")
    @Size(max = 9, message = "The length of the comment image address array cannot exceed 9 Zhang")
    private List<String> picUrls;

}
