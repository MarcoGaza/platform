package cn.econets.blossom.module.infrastructure.controller.admin.file.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - File Response VO,Do not return content Field，Too big")
@Data
public class FileRespVO {

    @Schema(description = "File number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Configuration number", requiredMode = Schema.RequiredMode.REQUIRED, example = "11")
    private Long configId;

    @Schema(description = "File path", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.jpg")
    private String path;

    @Schema(description = "Original file name", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.jpg")
    private String name;

    @Schema(description = "File URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.jpg")
    private String url;

    @Schema(description = "FileMIMEType", example = "application/octet-stream")
    private String type;

    @Schema(description = "File size", example = "2048", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer size;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
