package cn.econets.blossom.module.infrastructure.controller.admin.file.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "Management Backend - File configuration creation/Modify Request VO")
@Data
public class FileConfigSaveReqVO {

    @Schema(description = "Number", example = "1")
    private Long id;

    @Schema(description = "Configuration name", requiredMode = Schema.RequiredMode.REQUIRED, example = "S3 - Alibaba Cloud")
    @NotNull(message = "Configuration name cannot be empty")
    private String name;

    @Schema(description = "Memory，See FileStorageEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The storage cannot be empty")
    private Integer storage;

    @Schema(description = "Storage Configuration,Configuration is a dynamic parameter，So use Map Receive", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Storage configuration cannot be empty")
    private Map<String, Object> config;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

}
