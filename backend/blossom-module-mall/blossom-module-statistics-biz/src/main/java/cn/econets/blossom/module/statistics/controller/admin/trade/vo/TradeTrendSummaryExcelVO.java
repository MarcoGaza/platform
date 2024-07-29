package cn.econets.blossom.module.statistics.controller.admin.trade.vo;

import cn.econets.blossom.framework.excel.core.convert.MoneyConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDate;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * Transaction Statistics Excel VO
 *
 */
@Data
public class TradeTrendSummaryExcelVO {

    @ExcelProperty(value = "Date")
    @DateTimeFormat(FORMAT_YEAR_MONTH_DAY)
    private LocalDate date;

    @ExcelProperty(value = "Sales", converter = MoneyConvert.class)
    private Integer turnoverPrice;

    @ExcelProperty(value = "Product payment amount", converter = MoneyConvert.class)
    private Integer orderPayPrice;

    @ExcelProperty(value = "Recharge amount", converter = MoneyConvert.class)
    private Integer rechargePrice;

    @ExcelProperty(value = "Expenditure amount", converter = MoneyConvert.class)
    private Integer expensePrice;

    @ExcelProperty(value = "Balance payment amount", converter = MoneyConvert.class)
    private Integer walletPayPrice;

    @ExcelProperty(value = "Amount of commission paid", converter = MoneyConvert.class)
    private Integer brokerageSettlementPrice;

    @ExcelProperty(value = "Product refund amount", converter = MoneyConvert.class)
    private Integer afterSaleRefundPrice;
}
