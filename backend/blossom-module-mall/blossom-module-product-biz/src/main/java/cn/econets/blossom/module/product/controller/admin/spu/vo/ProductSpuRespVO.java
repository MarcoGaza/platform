package cn.econets.blossom.module.product.controller.admin.spu.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.framework.excel.core.convert.MoneyConvert;
import cn.econets.blossom.module.product.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Products SPU Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductSpuRespVO {

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    @ExcelProperty("Product Number")
    private Long id;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool short sleeves")
    @ExcelProperty("Product Name")
    private String name;

    @Schema(description = "Keywords", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool and smooth without sweating")
    @ExcelProperty("Keywords")
    private String keyword;

    @Schema(description = "Product Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "Introduction to cool short-sleeved shirts")
    @ExcelProperty("Product Introduction")
    private String introduction;

    @Schema(description = "Product details", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool short-sleeved shirt details")
    @ExcelProperty("Product details")
    private String description;

    @Schema(description = "Product Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Product Category Number")
    private Long categoryId;

    @Schema(description = "Product brand number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Product brand number")
    private Long brandId;

    @Schema(description = "Product cover image", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    @ExcelProperty("Product cover image")
    private String picUrl;

    @Schema(description = "Product Carousel", requiredMode = Schema.RequiredMode.REQUIRED, example = "[https://www.econets.cn/xx.png, https://www.econets.cn/xxx.png]")
    private List<String> sliderPicUrls;

    @Schema(description = "Sort Field", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Sort Field")
    private Integer sort;

    @Schema(description = "Product Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Product Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.PRODUCT_SPU_STATUS)
    private Integer status;

    @Schema(description = "Product creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-05-24 00:00:00")
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    // ========== SKU Related fields =========

    @Schema(description = "Specification type", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @ExcelProperty("Specification type")
    private Boolean specType;

    @Schema(description = "Product price", requiredMode = Schema.RequiredMode.REQUIRED, example = "1999")
    @ExcelProperty(value = "Product price", converter = MoneyConvert.class)
    private Integer price;

    @Schema(description = "Market price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "199")
    @ExcelProperty(value = "Market price", converter = MoneyConvert.class)
    private Integer marketPrice;

    @Schema(description = "Cost price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "19")
    @ExcelProperty(value = "Cost price", converter = MoneyConvert.class)
    private Integer costPrice;

    @Schema(description = "Product Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "10000")
    @ExcelProperty("Inventory")
    private Integer stock;

    @Schema(description = "SKU Array")
    private List<ProductSkuRespVO> skus;

    // ========== Logistics related fields =========

    @Schema(description = "Shipping method array", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private List<Integer> deliveryTypes;

    @Schema(description = "Logistics configuration template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    @ExcelProperty("Logistics configuration template number")
    private Long deliveryTemplateId;

    // ========== Marketing related fields =========

    @Schema(description = "Send points", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    @ExcelProperty("Send points")
    private Integer giveIntegral;

    @Schema(description = "Distribution Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @ExcelProperty("Distribution Type")
    private Boolean subCommissionType;

    // ========== Statistics related fields =========

    @Schema(description = "Product Sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000")
    @ExcelProperty("Product Sales")
    private Integer salesCount;

    @Schema(description = "Virtual sales", example = "66")
    @ExcelProperty("Virtual sales")
    private Integer virtualSalesCount;

    @Schema(description = "Views", requiredMode = Schema.RequiredMode.REQUIRED, example = "888")
    @ExcelProperty("Product clicks")
    private Integer browseCount;

}
