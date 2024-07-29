package cn.econets.blossom.module.mp.controller.admin.material.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Pagination of public account materials Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpMaterialPageReqVO extends PageParam {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotNull(message = "The public account number cannot be empty")
    private Long accountId;

    @Schema(description = "Is it permanent?", example = "true")
    private Boolean permanent;

    @Schema(description = "File type See WxConsts.MediaFileType Enumeration", example = "image")
    private String type;

}
