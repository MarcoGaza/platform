package cn.econets.blossom.module.promotion.controller.app.activity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "User App - Marketing Activities Response VO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppActivityRespVO {

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Activity Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type; // Corresponding PromotionTypeEnum Enumeration

    @Schema(description = "Activity Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "618 Big Sale")
    private String name;

    @Schema(description = "spu Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "618")
    private Long spuId;

    @Schema(description = "Activity start time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    @Schema(description = "Event end time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

}
