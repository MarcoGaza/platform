package cn.econets.blossom.module.member.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Member Tag Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberTagBaseVO {

    @Schema(description = "Tag name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotNull(message = "Tag name cannot be empty")
    private String name;

}
