package cn.econets.blossom.module.bpm.controller.admin.definition.vo.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
* User Group Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class BpmUserGroupBaseVO {

    @Schema(description = "Group name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    @NotNull(message = "Group name cannot be empty")
    private String name;

    @Schema(description = "Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Source code")
    @NotNull(message = "Description cannot be empty")
    private String description;

    @Schema(description = "Member number array", requiredMode = Schema.RequiredMode.REQUIRED, example = "1,2,3")
    @NotNull(message = "The member number array cannot be empty")
    private Set<Long> memberUserIds;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

}
