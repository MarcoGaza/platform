package cn.econets.blossom.module.system.controller.admin.dict.vo.data;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Dictionary data information Response VO")
@Data
@ExcelIgnoreUnannotated
public class DictDataRespVO {

    @Schema(description = "Dictionary data number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Dictionary encoding")
    private Long id;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Dictionary sort")
    private Integer sort;

    @Schema(description = "Dictionary tags", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @ExcelProperty("Dictionary tags")
    private String label;

    @Schema(description = "Dictionary value", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @ExcelProperty("Dictionary key value")
    private String value;

    @Schema(description = "Dictionary type", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_common_sex")
    @ExcelProperty("Dictionary type")
    private String dictType;

    @Schema(description = "Status,See you CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "Color type,default、primary、success、info、warning、danger", example = "default")
    private String colorType;

    @Schema(description = "css Style", example = "btn-visible")
    private String cssClass;

    @Schema(description = "Remarks", example = "I am a character")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    private LocalDateTime createTime;

}
