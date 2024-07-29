package cn.econets.blossom.module.trade.controller.app.brokerage.vo.record;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Application App - Distribution record paging Request VO")
@Data
public class AppBrokerageRecordPageReqVO extends PageParam {

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "Business Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(value = BrokerageRecordBizTypeEnum.class, message = "Business type must be {value}")
    private Integer bizType;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(value = BrokerageRecordStatusEnum.class, message = "The status must be {value}")
    private Integer status;

}
