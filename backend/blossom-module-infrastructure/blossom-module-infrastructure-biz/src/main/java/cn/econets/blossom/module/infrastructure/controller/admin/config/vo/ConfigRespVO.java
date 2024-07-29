package cn.econets.blossom.module.infrastructure.controller.admin.config.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Parameter configuration information Response VO")
@Data
@ExcelIgnoreUnannotated
public class ConfigRespVO {

    @Schema(description = "Parameter configuration serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Parameter configuration serial number")
    private Long id;

    @Schema(description = "Parameter classification", requiredMode = Schema.RequiredMode.REQUIRED, example = "biz")
    @ExcelProperty("Parameter classification")
    private String category;

    @Schema(description = "Parameter name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Database name")
    @ExcelProperty("Parameter name")
    private String name;

    @Schema(description = "Parameter key name", requiredMode = Schema.RequiredMode.REQUIRED, example = "ximu.db.username")
    @ExcelProperty("Parameter key name")
    private String key;

    @Schema(description = "Parameter key value", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Parameter key value")
    private String value;

    @Schema(description = "Parameter type，See SysConfigTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Parameter type", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CONFIG_TYPE)
    private Integer type;

    @Schema(description = "Is it visible?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @ExcelProperty(value = "Is it visible?", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean visible;

    @Schema(description = "Remarks", example = "Note that it is very handsome！")
    @ExcelProperty("Remarks")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
