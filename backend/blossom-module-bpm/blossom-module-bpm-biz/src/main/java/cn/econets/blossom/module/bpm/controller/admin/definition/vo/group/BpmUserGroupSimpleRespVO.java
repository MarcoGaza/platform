package cn.econets.blossom.module.bpm.controller.admin.definition.vo.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Management Backend - User group simplified information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BpmUserGroupSimpleRespVO {

    @Schema(description = "User group number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "User Group Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

}
