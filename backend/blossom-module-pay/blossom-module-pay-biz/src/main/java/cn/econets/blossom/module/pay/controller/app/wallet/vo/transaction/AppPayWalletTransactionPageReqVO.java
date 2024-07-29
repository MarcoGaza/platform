package cn.econets.blossom.module.pay.controller.app.wallet.vo.transaction;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "User APP - Wallet transaction paging Request VO")
@Data
public class AppPayWalletTransactionPageReqVO extends PageParam {

    /**
     * Type - Income
     */
    public static final Integer TYPE_INCOME = 1;
    /**
     * Type - Expenditure
     */
    public static final Integer TYPE_EXPENSE = 2;

    @Schema(description = "Type",  example = "1")
    private Integer type;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
