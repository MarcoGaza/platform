package cn.econets.blossom.module.crm.controller.admin.clue.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Clue paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmCluePageReqVO extends PageParam {

    @Schema(description = "Clue Name", example = "Cluesxxx")
    private String name;

    @Schema(description = "Phone", example = "18000000000")
    private String telephone;

    @Schema(description = "Mobile phone number", example = "18000000000")
    private String mobile;

    @Schema(description = "Scene Type", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // Scene Type，for null When it means all

    @Schema(description = "Is it high seas data?", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private Boolean pool; // null It means it is not high seas data

    @Schema(description = "Industry", example = "1")
    private Integer industryId;

    @Schema(description = "Customer Level", example = "1")
    private Integer level;

    @Schema(description = "Customer Source", example = "1")
    private Integer source;

}
