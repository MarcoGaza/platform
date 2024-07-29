package cn.econets.blossom.module.trade.controller.app.aftersale.vo.log;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - App Transaction and after-sales log Response VO")
@Data
public class AppAfterSaleLogRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20669")
    private Long id;

    @Schema(description = "Operation details", requiredMode = Schema.RequiredMode.REQUIRED, example = "Rights protection completed，Refund amount：¥37776.00")
    private String content;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
