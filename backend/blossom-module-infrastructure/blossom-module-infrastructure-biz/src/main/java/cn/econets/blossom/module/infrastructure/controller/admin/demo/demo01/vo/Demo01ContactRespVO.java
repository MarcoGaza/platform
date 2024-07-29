package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Sample Contact Response VO")
@Data
@ExcelIgnoreUnannotated
public class Demo01ContactRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "21555")
    @ExcelProperty("Number")
    private Long id;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhang San")
    @ExcelProperty("Name")
    private String name;

    @Schema(description = "Gender", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Gender", converter = DictConvert.class)
    @DictFormat("system_user_sex") // TODO Code optimization：It is recommended to set it to the corresponding setting DictTypeConstants In enumeration class
    private Integer sex;

    @Schema(description = "Year of Birth", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Year of Birth")
    private LocalDateTime birthday;

    @Schema(description = "Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "You are right")
    @ExcelProperty("Introduction")
    private String description;

    @Schema(description = "Avatar")
    @ExcelProperty("Avatar")
    private String avatar;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
