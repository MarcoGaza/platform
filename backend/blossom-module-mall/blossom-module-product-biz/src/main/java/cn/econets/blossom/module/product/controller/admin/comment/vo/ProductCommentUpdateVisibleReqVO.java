package cn.econets.blossom.module.product.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Product reviews can be modified Request VO")
@Data
@ToString(callSuper = true)
public class ProductCommentUpdateVisibleReqVO {

    @Schema(description = "Evaluation number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15721")
    @NotNull(message = "The evaluation number cannot be empty")
    private Long id;

    @Schema(description = "Is it visible?", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull(message = "Whether it is visible cannot be empty")
    private Boolean visible;

}
