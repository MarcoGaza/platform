package cn.econets.blossom.module.infrastructure.controller.admin.file.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Upload file Request VO")
@Data
public class FileUploadReqVO {

    @Schema(description = "File attachment", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "File attachment cannot be empty")
    private MultipartFile file;

    @Schema(description = "File attachment", example = "file.png")
    private String path;

}
