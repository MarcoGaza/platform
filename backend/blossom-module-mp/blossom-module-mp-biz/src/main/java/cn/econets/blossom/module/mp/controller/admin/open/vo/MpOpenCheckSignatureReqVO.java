package cn.econets.blossom.module.mp.controller.admin.open.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Public account verification signature Request VO")
@Data
public class MpOpenCheckSignatureReqVO {

    @Schema(description = "WeChat encrypted signature", requiredMode = Schema.RequiredMode.REQUIRED, example = "490eb57f448b87bd5f20ccef58aa4de46aa1908e")
    @NotEmpty(message = "WeChat encrypted signature cannot be empty")
    private String signature;

    @Schema(description = "Timestamp", requiredMode = Schema.RequiredMode.REQUIRED, example = "1672587863")
    @NotEmpty(message = "Timestamp cannot be empty")
    private String timestamp;

    @Schema(description = "Random number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1827365808")
    @NotEmpty(message = "Random number cannot be empty")
    private String nonce;

    @Schema(description = "Random string", requiredMode = Schema.RequiredMode.REQUIRED, example = "2721154047828672511")
    @NotEmpty(message = "Random string cannot be empty")
    @SuppressWarnings("SpellCheckingInspection")
    private String echostr;

}
