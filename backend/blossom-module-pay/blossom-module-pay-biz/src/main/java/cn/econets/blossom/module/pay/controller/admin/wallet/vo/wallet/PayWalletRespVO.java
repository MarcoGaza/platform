package cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - User wallet Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayWalletRespVO extends PayWalletBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "29528")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "King**")
    private String nickname;
    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.jpg")
    private String avatar;

}
