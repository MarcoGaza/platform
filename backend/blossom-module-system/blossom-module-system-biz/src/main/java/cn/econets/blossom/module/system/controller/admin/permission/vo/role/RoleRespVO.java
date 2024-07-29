package cn.econets.blossom.module.system.controller.admin.permission.vo.role;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Management Backend - Character information Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoleRespVO {

    @Schema(description = "Role number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Character serial number")
    private Long id;

    @Schema(description = "Role name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Administrator")
    @ExcelProperty("Role name")
    private String name;

    @NotBlank(message = "Role flag cannot be empty")
    @ExcelProperty("Role Logo")
    private String code;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Role sorting")
    private Integer sort;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Character Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "Role type，See RoleTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "Remarks", example = "I am a character")
    private String remark;

    @Schema(description = "Data Range，See DataScopeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Data Range")
    private Integer dataScope;

    @Schema(description = "Data Range(Specify department array)", example = "1")
    private Set<Long> dataScopeDeptIds;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    private LocalDateTime createTime;

}
