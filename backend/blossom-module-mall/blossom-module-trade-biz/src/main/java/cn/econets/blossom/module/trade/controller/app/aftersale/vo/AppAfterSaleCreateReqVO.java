package cn.econets.blossom.module.trade.controller.app.aftersale.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleWayEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "User App - Create after transaction Request VO")
@Data
public class AppAfterSaleCreateReqVO {

    @Schema(description = "Order Item Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The order item number cannot be empty")
    private Long orderItemId;

    @Schema(description = "After-sales service", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "After-sales method cannot be empty")
    @InEnum(value = AfterSaleWayEnum.class, message = "After-sales method must be {value}")
    private Integer way;

    @Schema(description = "Refund amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "Refund amount cannot be empty")
    @Min(value = 1, message = "Refund amount must be greater than 0")
    private Integer refundPrice;

    @Schema(description = "Reason for application", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Application reason cannot be empty")
    private String applyReason;

    @Schema(description = "Additional description", example = "Poor product quality")
    private String applyDescription;

    @Schema(description = "Supplementary voucher image", example = "https://www.econets.cn/1.png, https://www.econets.cn/2.png")
    private List<String> applyPicUrls;

}
