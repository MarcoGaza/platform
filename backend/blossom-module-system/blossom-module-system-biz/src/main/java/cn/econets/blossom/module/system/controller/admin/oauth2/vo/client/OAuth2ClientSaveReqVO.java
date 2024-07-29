package cn.econets.blossom.module.system.controller.admin.oauth2.vo.client;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - OAuth2 Client creation/Modify Request VO")
@Data
public class OAuth2ClientSaveReqVO {

    @Schema(description = "Number", example = "1024")
    private Long id;

    @Schema(description = "Client ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "tudou")
    @NotNull(message = "Client ID cannot be empty")
    private String clientId;

    @Schema(description = "Client Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "fan")
    @NotNull(message = "Client key cannot be empty")
    private String secret;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    @NotNull(message = "Application name cannot be empty")
    private String name;

    @Schema(description = "Application Icon", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    @NotNull(message = "The application icon cannot be empty")
    @URL(message = "The address of the application icon is incorrect")
    private String logo;

    @Schema(description = "Application Description", example = "I am an application")
    private String description;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Access token validity period", requiredMode = Schema.RequiredMode.REQUIRED, example = "8640")
    @NotNull(message = "The validity period of the access token cannot be empty")
    private Integer accessTokenValiditySeconds;

    @Schema(description = "Validity period of refresh token", requiredMode = Schema.RequiredMode.REQUIRED, example = "8640000")
    @NotNull(message = "The validity period of the refresh token cannot be empty")
    private Integer refreshTokenValiditySeconds;

    @Schema(description = "Redirectable URI Address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn")
    @NotNull(message = "Redirectable URI Address cannot be empty")
    private List<@NotEmpty(message = "Redirected URI Cannot be empty") @URL(message = "Redirected URI Incorrect format") String> redirectUris;

    @Schema(description = "Authorization type，See OAuth2GrantTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "password")
    @NotNull(message = "Authorization type cannot be empty")
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

    @AssertTrue(message = "Additional information must be JSON Format")
    public boolean isAdditionalInformationJson() {
        return StrUtil.isEmpty(additionalInformation) || JsonUtils.isJson(additionalInformation);
    }

}
