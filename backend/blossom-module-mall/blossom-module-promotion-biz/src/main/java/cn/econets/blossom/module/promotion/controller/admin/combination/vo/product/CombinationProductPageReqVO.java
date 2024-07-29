package cn.econets.blossom.module.promotion.controller.admin.combination.vo.product;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Group buy product paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationProductPageReqVO extends PageParam {

    @Schema(description = "Group buying activity number", example = "6829")
    private Long activityId;

    @Schema(description = "Products SPU Number", example = "18731")
    private Long spuId;

    @Schema(description = "Products SKU Number", example = "31675")
    private Long skuId;

    @Schema(description = "Group buy product status", example = "2")
    private Integer activityStatus;

    @Schema(description = "Activity start time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] activityStartTime;

    @Schema(description = "Event end time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] activityEndTime;

    @Schema(description = "Group buying price，Unit points", example = "27682")
    private Integer activePrice;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
