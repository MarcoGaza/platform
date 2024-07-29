package cn.econets.blossom.module.trade.controller.admin.config.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageBindModeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageEnabledConditionEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Trading Center Configuration Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class TradeConfigBaseVO {

    // ========== After-sales related ==========

    @Schema(description = "Reason for after-sales refund", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "The reason for after-sales refund cannot be empty")
    private List<String> afterSaleRefundReasons;

    @Schema(description = "Reasons for after-sales returns", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "The reason for after-sales return cannot be empty")
    private List<String> afterSaleReturnReasons;

    // ========== Delivery related ==========

    /**
     * Whether to enable free shipping for the entire site
     */
    @Schema(description = "Whether to enable free shipping for the entire site", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to enable free shipping for the entire site cannot be empty")
    private Boolean deliveryExpressFreeEnabled;

    @Schema(description = "Minimum amount for free shipping", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @NotNull(message = "The minimum amount for free shipping cannot be empty")
    @PositiveOrZero(message = "The minimum amount for free shipping cannot be a negative number")
    private Integer deliveryExpressFreePrice;

    @Schema(description = "Whether to enable self-collection", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to enable self-collection cannot be empty")
    private Boolean deliveryPickUpEnabled;

    // ========== Distribution related ==========

    @Schema(description = "Whether to enable commission sharing", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to enable commission cannot be empty")
    private Boolean brokerageEnabled;

    @Schema(description = "Commission model", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "Commission mode cannot be empty")
    @InEnum(value = BrokerageEnabledConditionEnum.class, message = "The commission mode must be {value}")
    private Integer brokerageEnabledCondition;

    @Schema(description = "Distribution relationship binding mode", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "The distribution relationship binding mode cannot be empty")
    @InEnum(value = BrokerageBindModeEnum.class, message = "The distribution relationship binding mode must be {value}")
    private Integer brokerageBindMode;

    @Schema(description = "Distribution poster image address array", requiredMode = Schema.RequiredMode.REQUIRED, example = "[https://www.econets.cn/blossom.jpg]")
    private List<String> brokeragePosterUrls;

    @Schema(description = "First-level rebate ratio", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "The first-level rebate ratio cannot be empty")
    @Range(min = 0, max = 100, message = "The first-level rebate ratio must be in 0 - 100 Between")
    private Integer brokerageFirstPercent;

    @Schema(description = "Second level rebate ratio", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "The secondary rebate ratio cannot be empty")
    @Range(min = 0, max = 100, message = "The secondary rebate ratio must be in 0 - 100 Between")
    private Integer brokerageSecondPercent;

    @Schema(description = "Minimum withdrawal amount for users", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @NotNull(message = "The minimum withdrawal amount cannot be empty")
    @PositiveOrZero(message = "The minimum withdrawal amount cannot be a negative number")
    private Integer brokerageWithdrawMinPrice;

    @Schema(description = "User withdrawal fee percentage", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @NotNull(message = "The user withdrawal fee percentage cannot be empty")
    @PositiveOrZero(message = "User withdrawal fee percentage cannot be negative")
    private Integer brokerageWithdrawFeePercent;

    @Schema(description = "Commission freeze time(Sky)", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    @NotNull(message = "Commission freeze time(Sky)Cannot be empty")
    @PositiveOrZero(message = "Commission freeze time cannot be negative")
    private Integer brokerageFrozenDays;

    @Schema(description = "Withdrawal method", requiredMode = Schema.RequiredMode.REQUIRED, example = "[0, 1]")
    @NotEmpty(message = "Withdrawal method cannot be empty")
    @InEnum(value = BrokerageWithdrawTypeEnum.class, message = "The withdrawal method must be {value}")
    private List<Integer> brokerageWithdrawTypes;

}
