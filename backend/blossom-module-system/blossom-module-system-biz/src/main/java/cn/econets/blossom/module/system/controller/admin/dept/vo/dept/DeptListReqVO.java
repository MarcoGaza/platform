package cn.econets.blossom.module.system.controller.admin.dept.vo.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Department List Request VO")
@Data
public class DeptListReqVO {

    @Schema(description = "Department Name，Fuzzy matching", example = "blossom")
    private String name;

    @Schema(description = "Display status，See CommonStatusEnum Enumeration class", example = "1")
    private Integer status;

}
