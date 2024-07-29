package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Example Category Response VO")
@Data
@ExcelIgnoreUnannotated
public class Demo02CategoryRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10304")
    @ExcelProperty("Number")
    private Long id;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @ExcelProperty("Name")
    private String name;

    @Schema(description = "Parent number", requiredMode = Schema.RequiredMode.REQUIRED, example = "6080")
    @ExcelProperty("Parent number")
    private Long parentId;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
