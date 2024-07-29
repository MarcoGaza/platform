package cn.econets.blossom.module.mp.controller.admin.material.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Results of uploading materials to the public account Response VO")
@Data
public class MpMaterialUploadRespVO {

    @Schema(description = "Material media_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "123")
    private String mediaId;

    @Schema(description = "Material URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String url;

}
