package cn.econets.blossom.module.statistics.controller.admin.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "Management Backend - Product Statistics Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductStatisticsRespVO {

    @Schema(description = "Number，Primary key auto-increment", requiredMode = Schema.RequiredMode.REQUIRED, example = "12393")
    private Long id;

    @Schema(description = "Statistical date", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-12-16")
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @ExcelProperty("Statistical date")
    private LocalDate time;

    @Schema(description = "ProductsSPUNumber", requiredMode = Schema.RequiredMode.REQUIRED, example = "15114")
    @ExcelProperty("ProductsSPUNumber")
    private Long spuId;

    // region Product Information

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Product Name")
    @ExcelProperty("Product Name")
    private String name;

    @Schema(description = "Product cover image", requiredMode = Schema.RequiredMode.REQUIRED, example = "15114")
    @ExcelProperty("Product cover image")
    private String picUrl;

    // endregion

    @Schema(description = "Views", requiredMode = Schema.RequiredMode.REQUIRED, example = "17505")
    @ExcelProperty("Views")
    private Integer browseCount;

    @Schema(description = "Number of visitors", requiredMode = Schema.RequiredMode.REQUIRED, example = "11814")
    @ExcelProperty("Number of visitors")
    private Integer browseUserCount;

    @Schema(description = "Number of collections", requiredMode = Schema.RequiredMode.REQUIRED, example = "20950")
    @ExcelProperty("Number of favorites")
    private Integer favoriteCount;

    @Schema(description = "Add to purchase quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "28493")
    @ExcelProperty("Add to purchase quantity")
    private Integer cartCount;

    @Schema(description = "Number of Orders", requiredMode = Schema.RequiredMode.REQUIRED, example = "18966")
    @ExcelProperty("Number of items ordered")
    private Integer orderCount;

    @Schema(description = "Number of payments", requiredMode = Schema.RequiredMode.REQUIRED, example = "15142")
    @ExcelProperty("Number of payments")
    private Integer orderPayCount;

    @Schema(description = "Payment amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "11595")
    @ExcelProperty("Payment amount，Unit：Points")
    private Integer orderPayPrice;

    @Schema(description = "Number of refunds", requiredMode = Schema.RequiredMode.REQUIRED, example = "2591")
    @ExcelProperty("Number of refunds")
    private Integer afterSaleCount;

    @Schema(description = "Refund amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "21709")
    @ExcelProperty("Refund amount，Unit：Points")
    private Integer afterSaleRefundPrice;

    @Schema(description = "Visitor payment conversion rate（Percentage）", requiredMode = Schema.RequiredMode.REQUIRED, example = "15")
    private Integer browseConvertPercent;

}