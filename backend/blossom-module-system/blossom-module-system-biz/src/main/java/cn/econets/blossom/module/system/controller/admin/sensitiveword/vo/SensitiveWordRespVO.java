package cn.econets.blossom.module.system.controller.admin.sensitiveword.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.framework.excel.core.convert.JsonConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Sensitive words Response VO")
@Data
public class SensitiveWordRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Number")
    private Long id;

    @Schema(description = "Sensitive words", requiredMode = Schema.RequiredMode.REQUIRED, example = "Sensitive words")
    @ExcelProperty("Sensitive words")
    private String name;

    @Schema(description = "Tag", requiredMode = Schema.RequiredMode.REQUIRED, example = "SMS,Comments")
    @ExcelProperty(value = "Tag", converter = JsonConvert.class)
    private List<String> tags;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "Description", example = "Dirty words")
    @ExcelProperty("Description")
    private String description;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
