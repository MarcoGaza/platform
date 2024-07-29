package cn.econets.blossom.module.product.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Merchant's reply to product reviews Request VO")
@Data
@ToString(callSuper = true)
public class ProductCommentReplyReqVO {

    @Schema(description = "Evaluation number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15721")
    @NotNull(message = "The evaluation number cannot be empty")
    private Long id;

    @Schema(description = "Merchant's reply content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Thank you dear")
    @NotEmpty(message = "Merchant reply content cannot be empty")
    private String replyContent;

}
