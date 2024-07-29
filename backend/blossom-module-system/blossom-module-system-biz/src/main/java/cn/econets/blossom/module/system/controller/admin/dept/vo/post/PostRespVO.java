package cn.econets.blossom.module.system.controller.admin.dept.vo.post;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Job Information Response VO")
@Data
@ExcelIgnoreUnannotated
public class PostRespVO {

    @Schema(description = "Position number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Position number")
    private Long id;

    @Schema(description = "Position Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little Potato")
    @ExcelProperty("Position Name")
    private String name;

    @Schema(description = "Position Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @ExcelProperty("Position Code")
    private String code;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Position sorting")
    private Integer sort;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "Remarks", example = "Happy Notes")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
