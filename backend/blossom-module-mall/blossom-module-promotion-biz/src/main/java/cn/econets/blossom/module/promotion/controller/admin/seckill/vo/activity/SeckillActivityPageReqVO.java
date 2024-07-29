package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity;

import cn.econets.blossom.framework.common.pojo.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "Management Backend - Second sale activity page Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SeckillActivityPageReqVO extends PageParam {

    @Schema(description = "Name of flash sale event", example = "Limited time sale at 9pm")
    private String name;

    @Schema(description = "Activity Status", example = "In progress")
    private Integer status;

    @Schema(description = "Second sale periodid", example = "1")
    private Long configId;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime[] createTime;

}
