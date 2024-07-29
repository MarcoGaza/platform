package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Commission withdrawal Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class BrokerageWithdrawBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "11436")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "Withdrawal amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "18781")
    @NotNull(message = "Withdrawal amount cannot be empty")
    private Integer price;

    @Schema(description = "Withdrawal fee", requiredMode = Schema.RequiredMode.REQUIRED, example = "11417")
    @NotNull(message = "Withdrawal fee cannot be empty")
    private Integer feePrice;

    @Schema(description = "Current total commission", requiredMode = Schema.RequiredMode.REQUIRED, example = "18576")
    @NotNull(message = "The current total commission cannot be empty")
    private Integer totalPrice;

    @Schema(description = "Withdrawal type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Withdrawal type cannot be empty")
    private Integer type;

    @Schema(description = "Real name", example = "Zhao Liu")
    private String name;

    @Schema(description = "Account", example = "88677912132")
    private String accountNo;

    @Schema(description = "Bank Name", example = "1")
    private String bankName;

    @Schema(description = "Account opening address", example = "Haidian Branch")
    private String bankAddress;

    @Schema(description = "Payment code", example = "https://www.econets.cn")
    private String accountQrCodeUrl;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Reason for rejection of review", example = "Incorrect")
    private String auditReason;

    @Schema(description = "Audit time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime auditTime;

    @Schema(description = "Remarks", example = "Whatever")
    private String remark;

}
