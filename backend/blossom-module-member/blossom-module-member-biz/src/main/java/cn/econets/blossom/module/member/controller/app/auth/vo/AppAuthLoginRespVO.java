package cn.econets.blossom.module.member.controller.app.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "User APP - Login Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthLoginRespVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "Access Token", requiredMode = Schema.RequiredMode.REQUIRED, example = "happy")
    private String accessToken;

    @Schema(description = "Refresh Token", requiredMode = Schema.RequiredMode.REQUIRED, example = "nice")
    private String refreshToken;

    @Schema(description = "Expiration time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime expiresTime;

    /**
     * Social login only、Will be returned when social binding
     *
     * Why do we need to return?？WeChat public account、WeChat applet payment requires transmission openid For payment interface
     */
    @Schema(description = "Social user openid", example = "qq768")
    private String openid;

}
