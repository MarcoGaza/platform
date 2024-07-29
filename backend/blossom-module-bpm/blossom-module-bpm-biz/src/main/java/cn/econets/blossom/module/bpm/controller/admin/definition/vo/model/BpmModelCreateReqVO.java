package cn.econets.blossom.module.bpm.controller.admin.definition.vo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Creation of process model Request VO")
@Data
public class BpmModelCreateReqVO {

    @Schema(description = "Process ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "process_blossom")
    @NotEmpty(message = "Process ID cannot be empty")
    private String key;

    @Schema(description = "Process name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    @NotEmpty(message = "Process name cannot be empty")
    private String name;

    @Schema(description = "Process description", example = "I am describing")
    private String description;

}
