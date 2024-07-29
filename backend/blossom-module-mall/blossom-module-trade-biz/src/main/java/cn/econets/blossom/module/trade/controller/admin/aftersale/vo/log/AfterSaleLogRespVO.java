package cn.econets.blossom.module.trade.controller.admin.aftersale.vo.log;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Transaction and after-sales log Response VO")
@Data
public class AfterSaleLogRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20669")
    private Long id;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22634")
    private Long userId;

    @Schema(description = "User Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer userType;

    @Schema(description = "After-sales number", requiredMode = Schema.RequiredMode.REQUIRED, example = "3023")
    private Long afterSaleId;

    @Schema(description = "After-sales status（Before）", example = "2")
    private Integer beforeStatus;

    @Schema(description = "After-sales status（After）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer afterStatus;

    @Schema(description = "Operation details", requiredMode = Schema.RequiredMode.REQUIRED, example = "Rights protection completed，Refund amount：¥37776.00")
    private String content;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
