package cn.econets.blossom.module.product.api.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Comment creation request DTO
 *
 */
@Data
public class ProductCommentCreateReqDTO {

    /**
     * Products SKU Number
     */
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;
    /**
     * Order number
     */
    private Long orderId;
    /**
     * Transaction order item number
     */
    private Long orderItemId;

    /**
     * Description star rating 1-5 Points
     */
    @NotNull(message = "The description star rating cannot be empty")
    private Integer descriptionScores;
    /**
     * Service Stars 1-5 Points
     */
    @NotNull(message = "Service star rating cannot be empty")
    private Integer benefitScores;
    /**
     * Comment content
     */
    @NotNull(message = "Comment content cannot be empty")
    private String content;
    /**
     * Comment picture address array，Upload maximum by comma separation 9 Zhang
     */
    private List<String> picUrls;

    /**
     * Is it anonymous?
     */
    @NotNull(message = "Whether anonymous cannot be empty")
    private Boolean anonymous;
    /**
     * Evaluator
     */
    @NotNull(message = "The reviewer cannot be empty")
    private Long userId;

}
