package cn.econets.blossom.module.system.controller.admin.dept.vo.post;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Simplification of job information Response VO")
@Data
public class PostSimpleRespVO {

    @Schema(description = "Position number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Position number")
    private Long id;

    @Schema(description = "Position Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little Potato")
    @ExcelProperty("Position Name")
    private String name;

}
