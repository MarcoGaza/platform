package cn.econets.blossom.module.system.controller.admin.dept.vo.post;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Management Backend - Position Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostPageReqVO extends PageParam {

    @Schema(description = "Position Code，Fuzzy matching", example = "admin")
    private String code;

    @Schema(description = "Position Name，Fuzzy matching", example = "blossom")
    private String name;

    @Schema(description = "Display status，See CommonStatusEnum Enumeration class", example = "1")
    private Integer status;

}
