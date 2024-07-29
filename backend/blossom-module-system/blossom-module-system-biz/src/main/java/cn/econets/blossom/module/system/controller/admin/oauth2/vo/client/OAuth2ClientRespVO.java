package cn.econets.blossom.module.system.controller.admin.oauth2.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - OAuth2 Client Response VO")
@Data
public class OAuth2ClientRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Client ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "tudou")
    private String clientId;

    @Schema(description = "Client Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "fan")
    private String secret;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    private String name;

    @Schema(description = "Application Icon", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    private String logo;

    @Schema(description = "Application Description", example = "I am an application")
    private String description;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Access token validity period", requiredMode = Schema.RequiredMode.REQUIRED, example = "8640")
    private Integer accessTokenValiditySeconds;

    @Schema(description = "Validity period of refresh token", requiredMode = Schema.RequiredMode.REQUIRED, example = "8640000")
    private Integer refreshTokenValiditySeconds;

    @Schema(description = "Redirectable URI Address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn")
    private List<String> redirectUris;

    @Schema(description = "Authorization type，See OAuth2GrantTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "password")
    private List<String> authorizedGrantTypes;

    @Schema(description = "Authorization scope", example = "user_info")
    private List<String> scopes;

    @Schema(description = "Automatically approved authorization scope", example = "user_info")
    private List<String> autoApproveScopes;

    @Schema(description = "Permissions", example = "system:user:query")
    private List<String> authorities;

    @Schema(description = "Resources", example = "1024")
    private List<String> resourceIds;

    @Schema(description = "Additional information", example = "{ximu: true}")
    private String additionalInformation;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
