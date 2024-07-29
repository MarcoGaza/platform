package cn.econets.blossom.module.system.controller.admin.oauth2.vo.open;

import cn.econets.blossom.framework.common.core.KeyValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Management Backend - Information of the authorization page Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2OpenAuthorizeInfoRespVO {

    /**
     * Client
     */
    private Client client;

    @Schema(description = "scope Selected information,Use List Ensure orderliness，Key Yes scope，Value Whether selected", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<KeyValue<String, Boolean>> scopes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Client {

        @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
        private String name;

        @Schema(description = "Application Icon", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
        private String logo;

    }

}
