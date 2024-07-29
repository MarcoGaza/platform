package cn.econets.blossom.module.promotion.controller.admin.diy.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Decoration template Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class DiyTemplateBaseVO {

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Default theme")
    @NotEmpty(message = "Template name cannot be empty")
    private String name;

    @Schema(description = "Remarks", example = "Default theme")
    private String remark;

    @Schema(description = "Preview image", example = "[https://www.econets.cn/1.jpg]")
    private List<String> previewPicUrls;

}
