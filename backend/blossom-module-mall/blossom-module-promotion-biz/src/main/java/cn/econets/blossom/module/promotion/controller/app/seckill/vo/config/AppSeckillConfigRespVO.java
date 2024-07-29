package cn.econets.blossom.module.promotion.controller.app.seckill.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Second sale time period Response VO")
@Data
public class AppSeckillConfigRespVO {

    @Schema(description = "Second sale time period number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Start time", requiredMode = Schema.RequiredMode.REQUIRED, example = "09:00")
    private String startTime;
    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED, example = "09:59")
    private String endTime;

    @Schema(description = "Slideshow", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> sliderPicUrls;

}
