package cn.econets.blossom.module.system.controller.admin.oauth2.vo.client;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - OAuth2 Client-side paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuth2ClientPageReqVO extends PageParam {

    @Schema(description = "Application name，Fuzzy matching", example = "Potato")
    private String name;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", example = "1")
    private Integer status;

}
