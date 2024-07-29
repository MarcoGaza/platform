package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Added new sample category/Modify Request VO")
@Data
public class Demo02CategorySaveReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10304")
    private Long id;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @Schema(description = "Parent number", requiredMode = Schema.RequiredMode.REQUIRED, example = "6080")
    @NotNull(message = "The parent number cannot be empty")
    private Long parentId;

}
