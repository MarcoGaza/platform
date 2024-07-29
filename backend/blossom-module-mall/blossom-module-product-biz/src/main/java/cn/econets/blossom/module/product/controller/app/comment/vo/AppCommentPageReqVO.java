package cn.econets.blossom.module.product.controller.app.comment.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Product Reviews Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppCommentPageReqVO extends PageParam {

    /**
     * Good reviews
     */
    public static final Integer GOOD_COMMENT = 1;
    /**
     * Medium Rating
     */
    public static final Integer MEDIOCRE_COMMENT = 2;
    /**
     * Bad review
     */
    public static final Integer NEGATIVE_COMMENT = 3;

    @Schema(description = "Products SPU Number", example = "29502")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Long spuId;

    @Schema(description = "app Comment page tab Type (0 All、1 Good reviews、2 Medium Rating、3 Bad review)", example = "0")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Integer type;

}
