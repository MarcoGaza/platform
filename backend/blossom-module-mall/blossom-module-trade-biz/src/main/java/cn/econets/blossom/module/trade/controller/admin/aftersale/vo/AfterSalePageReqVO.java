package cn.econets.blossom.module.trade.controller.admin.aftersale.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleStatusEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleTypeEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleWayEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Transaction and after-sales paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AfterSalePageReqVO extends PageParam {

    @Schema(description = "After-sales serial number", example = "202211190847450020500077")
    private String no;

    @Schema(description = "After-sales status", example = "10")
    @InEnum(value = AfterSaleStatusEnum.class, message = "After-sales status must be {value}")
    private Integer status;

    @Schema(description = "After-sales type", example = "20")
    @InEnum(value = AfterSaleTypeEnum.class, message = "After-sales type must be {value}")
    private Integer type;

    @Schema(description = "After-sales service", example = "10")
    @InEnum(value = AfterSaleWayEnum.class, message = "After-sales method must be {value}")
    private Integer way;

    @Schema(description = "Order Number", example = "18078")
    private String orderNo;

    @Schema(description = "Products SPU Name", example = "Li Si")
    private String spuName;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
