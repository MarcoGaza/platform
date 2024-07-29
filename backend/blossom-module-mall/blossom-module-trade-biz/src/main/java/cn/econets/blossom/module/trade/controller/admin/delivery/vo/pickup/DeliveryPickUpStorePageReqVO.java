package cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import lombok.*;

import java.time.LocalTime;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.econets.blossom.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Self-pickup store page Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryPickUpStorePageReqVO extends PageParam {

    @Schema(description = "Store Name", example = "Li Si")
    private String name;

    @Schema(description = "Store Mobile Phone")
    private String phone;

    @Schema(description = "Area Number", example = "18733")
    private Integer areaId;

    @Schema(description = "Store Status", example = "1")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
