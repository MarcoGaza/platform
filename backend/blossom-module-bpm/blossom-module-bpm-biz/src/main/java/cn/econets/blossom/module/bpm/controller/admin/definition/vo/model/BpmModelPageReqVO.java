package cn.econets.blossom.module.bpm.controller.admin.definition.vo.model;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Schema(description = "Management Backend - Process model paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmModelPageReqVO extends PageParam {

    @Schema(description = "Logo-Exact match", example = "process1641042089407")
    private String key;

    @Schema(description = "Name-Fuzzy matching", example = "Taro Road")
    private String name;

    @Schema(description = "Process Classification-See bpm_model_category Data dictionary", example = "1")
    private String category;

}
