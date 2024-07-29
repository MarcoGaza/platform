package cn.econets.blossom.module.product.controller.admin.spu.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Products SPU Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductSpuPageReqVO extends PageParam {

    /**
     * Items for sale
     */
    public static final Integer FOR_SALE = 0;

    /**
     * Goods in the warehouse
     */
    public static final Integer IN_WAREHOUSE = 1;

    /**
     * Sold out product
     */
    public static final Integer SOLD_OUT = 2;

    /**
     * Alert Inventory
     */
    public static final Integer ALERT_STOCK = 3;

    /**
     * Product Recycling Station
     */
    public static final Integer RECYCLE_BIN = 4;

    @Schema(description = "Product Name", example = "Cool short sleeves")
    private String name;

    @Schema(description = "Front-end requesttabType", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer tabType;

    @Schema(description = "Product Category Number", example = "1")
    private Long categoryId;

    @Schema(description = "Creation time", example = "[2022-07-01 00:00:00, 2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
