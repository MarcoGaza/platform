package cn.econets.blossom.module.system.controller.admin.socail.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Social client Response VO")
@Data
public class SocialClientRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "27162")
    private Long id;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossomMall")
    private String name;

    @Schema(description = "Type of social platform", requiredMode = Schema.RequiredMode.REQUIRED, example = "31")
    private Integer socialType;

    @Schema(description = "User Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer userType;

    @Schema(description = "Client ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "wwd411c69a39ad2e54")
    private String clientId;

    @Schema(description = "Client Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "peter")
    private String clientSecret;

    @Schema(description = "Web application ID of the authorized party", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000045")
    private String agentId;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
