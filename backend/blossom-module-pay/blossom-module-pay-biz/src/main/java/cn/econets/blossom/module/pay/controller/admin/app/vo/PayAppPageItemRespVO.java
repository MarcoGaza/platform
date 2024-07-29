package cn.econets.blossom.module.pay.controller.admin.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Management Backend - Payment application information paging query Response VO,Compared to payment information，There will also be additional switch information for application channels")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayAppPageItemRespVO extends PayAppBaseVO {

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Configured payment channel code", requiredMode = Schema.RequiredMode.REQUIRED, example = "[alipay_pc, alipay_wap]")
    private Set<String> channelCodes;

}
