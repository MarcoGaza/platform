package cn.econets.blossom.module.mp.controller.admin.open.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Public account processing message Request VO")
@Data
public class MpOpenHandleMessageReqVO {

    public static final String ENCRYPT_TYPE_AES = "aes";

    @Schema(description = "WeChat encrypted signature", requiredMode = Schema.RequiredMode.REQUIRED, example = "490eb57f448b87bd5f20ccef58aa4de46aa1908e")
    @NotEmpty(message = "WeChat encrypted signature cannot be empty")
    private String signature;

    @Schema(description = "Timestamp", requiredMode = Schema.RequiredMode.REQUIRED, example = "1672587863")
    @NotEmpty(message = "Timestamp cannot be empty")
    private String timestamp;

    @Schema(description = "Random number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1827365808")
    @NotEmpty(message = "Random number cannot be empty")
    private String nonce;

    @Schema(description = "Fans openid", requiredMode = Schema.RequiredMode.REQUIRED, example = "oz-Jdtyn-WGm4C4I5Z-nvBMO_ZfY")
    @NotEmpty(message = "Fans openid Cannot be empty")
    private String openid;

    @Schema(description = "Message encryption type", example = "aes")
    private String encrypt_type;

    @Schema(description = "WeChat signature", example = "QW5kcm9pZCBUaGUgQmFzZTY0IGlzIGEgZ2VuZXJhdGVkIHN0cmluZw==")
    private String msg_signature;

}
