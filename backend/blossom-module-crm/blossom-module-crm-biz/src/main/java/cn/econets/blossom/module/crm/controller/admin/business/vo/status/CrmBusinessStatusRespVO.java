package cn.econets.blossom.module.crm.controller.admin.business.vo.status;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Opportunity Status Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmBusinessStatusRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "23899")
    @ExcelProperty("Primary key")
    private Long id;

    @Schema(description = "Status type number", requiredMode = Schema.RequiredMode.REQUIRED, example = "7139")
    @ExcelProperty("Status type number")
    private Long typeId;

    @Schema(description = "Status name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wang Wu")
    @ExcelProperty("Status name")
    private String name;

    @Schema(description = "Winning rate")
    @ExcelProperty("Winning rate")
    private String percent;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Sort")
    private Integer sort;

}
