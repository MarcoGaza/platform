package cn.econets.blossom.module.system.controller.admin.errorcode.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Error code Response VO")
@Data
@ExcelIgnoreUnannotated
public class ErrorCodeRespVO {

    @Schema(description = "Error code number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Error code number")
    private Long id;

    @Schema(description = "Error code type，See ErrorCodeTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Error code type", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.ERROR_CODE_TYPE)
    private Integer type;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "dashboard")
    @ExcelProperty("Application name")
    private String applicationName;

    @Schema(description = "Error code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
    @ExcelProperty("Error code")
    private Integer code;

    @Schema(description = "Error code error prompt", requiredMode = Schema.RequiredMode.REQUIRED, example = "Handsome")
    @ExcelProperty("Error code error prompt")
    private String message;

    @Schema(description = "Remarks", example = "Hahaha")
    @ExcelProperty("Remarks")
    private String memo;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
