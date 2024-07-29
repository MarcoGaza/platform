package cn.econets.blossom.module.mp.controller.admin.material.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Images in the graphic content uploaded by the public account Request VO")
@Data
public class MpMaterialUploadNewsImageReqVO {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotNull(message = "The public account number cannot be empty")
    private Long accountId;

    @Schema(description = "File attachment", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The file cannot be empty")
    @JsonIgnore // Avoid manipulation logs，Serialize，Causes an error
    private MultipartFile file;

}
