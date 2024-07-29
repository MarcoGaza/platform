package cn.econets.blossom.module.system.controller.admin.socail.vo.client;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Social client paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SocialClientPageReqVO extends PageParam {

    @Schema(description = "Application name", example = "blossomMall")
    private String name;

    @Schema(description = "Type of social platform", example = "31")
    private Integer socialType;

    @Schema(description = "User Type", example = "2")
    private Integer userType;

    @Schema(description = "Client ID", example = "145442115")
    private String clientId;

    @Schema(description = "Status", example = "1")
    private Integer status;

}
