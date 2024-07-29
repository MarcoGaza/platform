package cn.econets.blossom.module.crm.controller.admin.product.vo.product;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.crm.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - CRM Products Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmProductRespVO {

    @Schema(description = "Product Number", example = "20529")
    @ExcelProperty("Product Number")
    private Long id;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Good product")
    @ExcelProperty("Product Name")
    private String name;

    @Schema(description = "Product Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "12306")
    @ExcelProperty("Product Code")
    private String no;

    @Schema(description = "Unit", example = "2")
    @ExcelProperty(value = "Unit", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CRM_PRODUCT_UNIT)
    private Integer unit;

    @Schema(description = "Price, Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "8911")
    @ExcelProperty("Price，Unit：Points")
    private Long price;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "On the shelf")
    @ExcelProperty(value = "Unit", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CRM_PRODUCT_STATUS)
    private Integer status;

    @Schema(description = "Product Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Long categoryId;
    @Schema(description = "Product Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Clothes")
    @ExcelProperty("Product Category")
    private String categoryName;

    @Schema(description = "Product Description", example = "You are right")
    @ExcelProperty("Product Description")
    private String description;

    @Schema(description = "User ID of the person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "31926")
    private Long ownerUserId;
    @Schema(description = "User nickname of the person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    @ExcelProperty("Person in charge")
    private String ownerUserName;

    @Schema(description = "Creator ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String creator;
    @Schema(description = "Creator's name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    @ExcelProperty("Creator")
    private String creatorName;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Update time")
    private LocalDateTime updateTime;

}
