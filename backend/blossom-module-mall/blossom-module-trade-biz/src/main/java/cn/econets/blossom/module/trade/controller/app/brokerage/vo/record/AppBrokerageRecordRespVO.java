package cn.econets.blossom.module.trade.controller.app.brokerage.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Distribution Records Response VO")
@Data
public class AppBrokerageRecordRespVO {

    @Schema(description = "Record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long id;

    @Schema(description = "Business Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private String bizId;

    @Schema(description = "Distribution Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "User places an order")
    private String title;

    @Schema(description = "Distribution amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer price;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Completion time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime finishTime;

}
