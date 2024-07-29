package cn.econets.blossom.module.pay.controller.admin.channel.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Payment channel Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayChannelRespVO extends PayChannelBaseVO {

    @Schema(description = "Merchant Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private LocalDateTime createTime;

    @Schema(description = "Channel Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "alipay_pc")
    private String code;

    @Schema(description = "Configuration", requiredMode = Schema.RequiredMode.REQUIRED)
    private String config;

}
