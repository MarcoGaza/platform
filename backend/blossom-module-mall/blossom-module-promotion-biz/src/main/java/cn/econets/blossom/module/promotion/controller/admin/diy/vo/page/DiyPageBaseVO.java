package cn.econets.blossom.module.promotion.controller.admin.diy.vo.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Decoration page Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class DiyPageBaseVO {

    @Schema(description = "Decoration template number", example = "26179")
    private Long templateId;

    @Schema(description = "Page Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wang Wu")
    @NotNull(message = "Page name cannot be empty")
    private String name;

    @Schema(description = "Remarks", example = "Whatever")
    private String remark;

    @Schema(description = "Preview image")
    private List<String> previewPicUrls;

}
