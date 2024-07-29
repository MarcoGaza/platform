package cn.econets.blossom.module.trade.controller.app.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Express tracking track Resp DTO
 *
 */
@Schema(description = "User App - Express tracking track Response VO")
@Data
public class AppOrderExpressTrackRespDTO {

    @Schema(description = "Occurrence time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime time;

    @Schema(description = "Express Delivery Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "Signed for")
    private String content;

}
