package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Commission withdrawal page Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrokerageWithdrawPageReqVO extends PageParam {

    @Schema(description = "User Number", example = "11436")
    private Long userId;

    @Schema(description = "Withdrawal type", example = "1")
    @InEnum(value = BrokerageWithdrawTypeEnum.class, message = "Withdrawal type must be {value}")
    private Integer type;

    @Schema(description = "Real name", example = "Zhao Liu")
    private String name;

    @Schema(description = "Account", example = "886779132")
    private String accountNo;

    @Schema(description = "Bank Name", example = "1")
    private String bankName;

    @Schema(description = "Status", example = "1")
    @InEnum(value = BrokerageWithdrawStatusEnum.class, message = "The status must be {value}")
    private Integer status;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
