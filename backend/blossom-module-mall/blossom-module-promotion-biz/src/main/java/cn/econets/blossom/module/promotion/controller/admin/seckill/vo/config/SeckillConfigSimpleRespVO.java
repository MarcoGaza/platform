package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Configure simplified information for flash sale periods Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillConfigSimpleRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The number cannot be empty")
    private Long id;

    @Schema(description = "Name of flash sale period", requiredMode = Schema.RequiredMode.REQUIRED, example = "Morning session")
    @NotNull(message = "The flash sale period name cannot be empty")
    private String name;

    @Schema(description = "Start time", requiredMode = Schema.RequiredMode.REQUIRED, example = "09:00:00")
    private String startTime;

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED, example = "16:00:00")
    private String endTime;

}
