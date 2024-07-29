package cn.econets.blossom.module.system.controller.admin.socail.vo.client;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Schema(description = "Management Backend - Social client creation/Modify Request VO")
@Data
public class SocialClientSaveReqVO {

    @Schema(description = "Number", example = "27162")
    private Long id;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossomMall")
    @NotNull(message = "Application name cannot be empty")
    private String name;

    @Schema(description = "Type of social platform", requiredMode = Schema.RequiredMode.REQUIRED, example = "31")
    @NotNull(message = "The type of social platform cannot be empty")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @Schema(description = "User Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "User type cannot be empty")
    @InEnum(UserTypeEnum.class)
    private Integer userType;

    @Schema(description = "Client ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "wwd411c69a39ad2e54")
    @NotNull(message = "Client ID cannot be empty")
    private String clientId;

    @Schema(description = "Client Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "peter")
    @NotNull(message = "The client key cannot be empty")
    private String clientSecret;

    @Schema(description = "Web application ID of the authorized party", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000045")
    private String agentId;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @AssertTrue(message = "agentId Cannot be empty")
    @JsonIgnore
    public boolean isAgentIdValid() {
        // If it is corporate WeChat，Required agentId Properties
        return !Objects.equals(socialType, SocialTypeEnum.WECHAT_ENTERPRISE.getType())
                || !StrUtil.isEmpty(agentId);
    }

}
