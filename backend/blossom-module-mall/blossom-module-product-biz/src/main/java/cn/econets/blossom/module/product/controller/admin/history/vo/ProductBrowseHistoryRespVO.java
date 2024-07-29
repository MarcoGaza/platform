package cn.econets.blossom.module.product.controller.admin.history.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Product browsing history Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductBrowseHistoryRespVO {

    @Schema(description = "Record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "26055")
    @ExcelProperty("Record number")
    private Long id;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4314")
    @ExcelProperty("User Number")
    private Long userId;

    @Schema(description = "Whether to delete the user", example = "false")
    private Boolean userDeleted;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "42")
    @ExcelProperty("Products SPU Number")
    private Long spuId;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}