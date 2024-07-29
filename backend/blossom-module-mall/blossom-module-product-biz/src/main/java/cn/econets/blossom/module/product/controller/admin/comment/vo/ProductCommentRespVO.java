package cn.econets.blossom.module.product.controller.admin.comment.vo;

import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSkuSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Product Reviews Response VO")
@Data
public class ProductCommentRespVO {

    @Schema(description = "Order Item Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "24965")
    private Long id;

    @Schema(description = "Evaluator", requiredMode = Schema.RequiredMode.REQUIRED, example = "16868")
    private Long userId;

    @Schema(description = "Name of the reviewer", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little girl")
    private String userNickname;

    @Schema(description = "Evaluator's avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    private String userAvatar;
    @Schema(description = "Is it anonymous?", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private Boolean anonymous;

    @Schema(description = "Transaction order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "24428")
    private Long orderId;

    @Schema(description = "Evaluation line item", requiredMode = Schema.RequiredMode.REQUIRED, example = "19292")
    private Long orderItemId;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool, silky and breathable short sleeves")
    private Long spuId;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long skuId;

    @Schema(description = "Products SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhao Liu")
    private String spuName;

    @Schema(description = "Products SKU Image address", example = "https://www.econets.cn/blossom.jpg")
    private String skuPicUrl;

    @Schema(description = "Products SKU Specification value array")
    private List<ProductSkuSaveReqVO.Property> skuProperties;

    @Schema(description = "Is it visible?", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean visible;

    @Schema(description = "Rating Stars 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer scores;

    @Schema(description = "Description star rating 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer descriptionScores;

    @Schema(description = "Service Stars 1-5 Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer benefitScores;

    @Schema(description = "Comment content", requiredMode = Schema.RequiredMode.REQUIRED, example = "It feels very smooth and cool to wear")
    private String content;

    @Schema(description = "Comment picture address array，Upload maximum comma separated 9 Zhang", requiredMode = Schema.RequiredMode.REQUIRED, example = "[https://www.econets.cn/xx.png]")
    private List<String> picUrls;

    @Schema(description = "Did the merchant reply?", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean replyStatus;

    @Schema(description = "Reply to the administrator number", example = "9527")
    private Long replyUserId;

    @Schema(description = "Merchant's reply content", example = "Thanks for your praise, dear(づ￣3￣)づ╭❤～")
    private String replyContent;

    @Schema(description = "Merchant response time", example = "2023-08-08 12:20:55")
    private LocalDateTime replyTime;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
