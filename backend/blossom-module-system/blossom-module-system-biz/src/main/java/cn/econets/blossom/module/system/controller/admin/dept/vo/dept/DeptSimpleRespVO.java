package cn.econets.blossom.module.system.controller.admin.dept.vo.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Management Backend - Department streamlining information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptSimpleRespVO {

    @Schema(description = "Department Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String name;

    @Schema(description = "Parent Department ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long parentId;

}
