package cn.econets.blossom.module.system.controller.admin.oauth2.vo.token;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Management Backend - Access token paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2AccessTokenPageReqVO extends PageParam {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    private Long userId;

    @Schema(description = "User Type，See UserTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer userType;

    @Schema(description = "Client ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private String clientId;

}
