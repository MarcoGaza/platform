package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

/**
 * Second sale period Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class SeckillConfigBaseVO {

    @Schema(description = "Name of flash sale period", requiredMode = Schema.RequiredMode.REQUIRED, example = "Morning session")
    @NotNull(message = "The flash sale period name cannot be empty")
    private String name;

    @Schema(description = "Starting time", requiredMode = Schema.RequiredMode.REQUIRED, example = "09:00:00")
    @NotNull(message = "The start time cannot be empty")
    private String startTime;

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED, example = "16:00:00")
    @NotNull(message = "The end time point cannot be empty")
    private String endTime;

    @Schema(description = "Second-selling carousel", requiredMode = Schema.RequiredMode.REQUIRED, example = "[https://www.econets.cn/xx.png]")
    @NotNull(message = "Second sale carousel image cannot be empty")
    private List<String> sliderPicUrls;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @AssertTrue(message = "The start time and end time of the flash sale period cannot be equal")
    @JsonIgnore
    public boolean isValidStartTimeValid() {
        return !LocalTime.parse(startTime).equals(LocalTime.parse(endTime));
    }

    @AssertTrue(message = "The start time of the flash sale period cannot be after the end time")
    @JsonIgnore
    public boolean isValidEndTimeValid() {
        return !LocalTime.parse(startTime).isAfter(LocalTime.parse(endTime));
    }

}
