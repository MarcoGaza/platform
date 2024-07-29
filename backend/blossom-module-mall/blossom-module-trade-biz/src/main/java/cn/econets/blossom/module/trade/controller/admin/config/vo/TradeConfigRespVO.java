package cn.econets.blossom.module.trade.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Trading Center Configuration Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TradeConfigRespVO extends TradeConfigBaseVO {

    @Schema(description = "Auto-increment primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Tencent Map KEY", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String tencentLbsKey;

}
