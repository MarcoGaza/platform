package cn.econets.blossom.module.mp.controller.admin.tag.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Paging of public account tags Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpTagPageReqVO extends PageParam {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotEmpty(message = "The public account number cannot be empty")
    private Long accountId;

    @Schema(description = "Tag name，Fuzzy matching", example = "Haha")
    private String name;

}
