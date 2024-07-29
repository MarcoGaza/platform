package cn.econets.blossom.module.infrastructure.controller.admin.file.vo.config;

import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - File Configuration Response VO")
@Data
public class FileConfigRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Configuration name", requiredMode = Schema.RequiredMode.REQUIRED, example = "S3 - Alibaba Cloud")
    private String name;

    @Schema(description = "Memory，See FileStorageEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer storage;

    @Schema(description = "Whether to be the main configuration", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean master;

    @Schema(description = "Storage Configuration", requiredMode = Schema.RequiredMode.REQUIRED)
    private FileClientConfig config;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
