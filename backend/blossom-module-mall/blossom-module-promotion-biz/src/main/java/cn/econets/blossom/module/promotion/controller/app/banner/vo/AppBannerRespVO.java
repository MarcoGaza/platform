package cn.econets.blossom.module.promotion.controller.app.banner.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Banner Response VO")
@Data
public class AppBannerRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Title cannot be empty")
    private String title;

    @Schema(description = "Jump link", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The jump link cannot be empty")
    private String url;

    @Schema(description = "Image address", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The image address cannot be empty")
    private String picUrl;

}
