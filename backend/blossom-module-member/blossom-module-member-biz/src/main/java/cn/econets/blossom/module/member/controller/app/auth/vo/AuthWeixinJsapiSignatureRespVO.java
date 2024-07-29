package cn.econets.blossom.module.member.controller.app.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "User APP - WeChat public account JSAPI Signature Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthWeixinJsapiSignatureRespVO {

    @Schema(description = "WeChat public account appId", requiredMode = Schema.RequiredMode.REQUIRED, example = "hello")
    private String appId;

    @Schema(description = "Anonymous string", requiredMode = Schema.RequiredMode.REQUIRED, example = "world")
    private String nonceStr;

    @Schema(description = "Timestamp", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long timestamp;

    @Schema(description = "URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn")
    private String url;

    @Schema(description = "Signature", requiredMode = Schema.RequiredMode.REQUIRED, example = "Aba Aba")
    private String signature;

}
