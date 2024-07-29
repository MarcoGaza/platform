package cn.econets.blossom.module.crm.controller.admin.product.vo.category;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - CRM Product Category List Request VO")
@Data
public class CrmProductCategoryListReqVO {

    @ExcelProperty("Name")
    private String name;

    @ExcelProperty("Parent id")
    private Long parentId;

    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
