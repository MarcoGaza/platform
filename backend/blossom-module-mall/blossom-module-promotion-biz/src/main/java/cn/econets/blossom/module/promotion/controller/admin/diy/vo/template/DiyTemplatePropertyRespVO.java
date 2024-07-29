package cn.econets.blossom.module.promotion.controller.admin.diy.vo.template;

import cn.econets.blossom.module.promotion.controller.admin.diy.vo.page.DiyPagePropertyRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "Management Backend - Decoration template properties Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiyTemplatePropertyRespVO extends DiyTemplateBaseVO {

    @Schema(description = "Decoration template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31209")
    private Long id;

    @Schema(description = "Template properties，JSON Format", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    private String property;

    @Schema(description = "Template page", requiredMode = Schema.RequiredMode.REQUIRED, example = "[]")
    private List<DiyPagePropertyRespVO> pages;

}
