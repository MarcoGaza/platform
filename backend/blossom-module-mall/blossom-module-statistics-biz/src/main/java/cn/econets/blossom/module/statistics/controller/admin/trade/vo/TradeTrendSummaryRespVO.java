package cn.econets.blossom.module.statistics.controller.admin.trade.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "Management Backend - Transaction Statistics Response VO")
@Data
public class TradeTrendSummaryRespVO {

    @Schema(description = "Date", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-12-16")
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate date;

    @Schema(description = "Sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer turnoverPrice; // Sales = Product payment amount + Recharge amount

    @Schema(description = "Order payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer orderPayPrice;

    @Schema(description = "Balance payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer walletPayPrice;

    @Schema(description = "Order refund amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer afterSaleRefundPrice;

    @Schema(description = "Amount of commission paid", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer brokerageSettlementPrice;

    @Schema(description = "Recharge amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer rechargePrice;

    @Schema(description = "Expenditure amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer expensePrice; // Balance payment amount + Amount of commission paid + Product refund amount

}
