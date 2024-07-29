package cn.econets.blossom.module.mp.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Public account tag Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 *
 */
@Data
public class MpTagBaseVO {

    @Schema(description = "Tag name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    @NotEmpty(message = "Tag name cannot be empty")
    private String name;

}
