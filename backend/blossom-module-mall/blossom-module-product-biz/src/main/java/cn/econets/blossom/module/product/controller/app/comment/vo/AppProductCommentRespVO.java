package cn.econets.blossom.module.product.controller.app.comment.vo;

import cn.econets.blossom.module.product.controller.app.property.vo.value.AppProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "User App - Product review details Response VO")
@Data
@ToString(callSuper = true)
public class AppProductCommentRespVO {

    @Schema(description = "The user ID of the reviewer", requiredMode = Schema.RequiredMode.REQUIRED, example = "15721")
    private Long userId;

    @Schema(description = "Name of the reviewer", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhang San")
    private String userNickname;

    @Schema(description = "Evaluator's avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    private String userAvatar;

    @Schema(description = "Order Item Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "24965")
    private Long id;

    @Schema(description = "Is it anonymous?", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private Boolean anonymous;

    @Schema(description = "Transaction order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "24428")
    private Long orderId;

    @Schema(description = "Transaction order item number", requiredMode = Schema.RequiredMode.REQUIRED, example = "8233")
    private Long orderItemId;

    @Schema(description = "Did the merchant reply?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean replyStatus;

    @Schema(description = "Reply to the administrator number", example = "22212")
    private Long replyUserId;

    @Schema(description = "Merchant's reply content", example = "Dear，Your praise is my motivation(*^▽^*)")
    private String replyContent;

    @Schema(description = "Merchant response time")
    private LocalDateTime replyTime;

    @Schema(description = "Add evaluation content", example = "It feels very smooth even after wearing it for a long time")
    private String additionalContent;

    @Schema(description = "An array of follow-up evaluation pictures' addresses，Upload maximum comma separated 9 Zhang", example = "[https://www.econets.cn/xx.png, https://www.econets.cn/xxx.png]")
    private List<String> additionalPicUrls;

    @Schema(description = "Add evaluation time")
    private LocalDateTime additionalTime;

    @Schema(description = "Creation time")
    private LocalDateTime createTime;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "91192")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Long spuId;

    @Schema(description = "Products SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool and silky short sleeves")
    @NotNull(message = "Products SPU The name cannot be empty")
    private String spuName;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "81192")
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;

    @Schema(description = "Products SKU Properties", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AppProductPropertyValueDetailRespVO> skuProperties;

    @Schema(description = "Rating Stars 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "Rating Stars 1-5 The score cannot be empty")
    private Integer scores;

    @Schema(description = "Description star rating 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "Description star rating 1-5 The score cannot be empty")
    private Integer descriptionScores;

    @Schema(description = "Service Stars 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "Service Stars 1-5 The score cannot be empty")
    private Integer benefitScores;

    @Schema(description = "Comment content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wow，It's really smooth and cool，Good reviews")
    @NotNull(message = "Comment content cannot be empty")
    private String content;

    @Schema(description = "Comment picture address array，Upload maximum comma separated 9 Zhang", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "[https://www.econets.cn/xx.png]")
    @Size(max = 9, message = "The length of the comment image address array cannot exceed 9 Zhang")
    private List<String> picUrls;

}
