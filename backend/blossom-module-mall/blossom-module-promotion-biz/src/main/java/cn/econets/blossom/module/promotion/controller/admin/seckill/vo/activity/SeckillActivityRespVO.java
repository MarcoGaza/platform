package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity;

import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.product.SeckillProductRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Second-sale event Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SeckillActivityRespVO extends SeckillActivityBaseVO {

    @Schema(description = "Second-sale event id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Second-selling products", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SeckillProductRespVO> products;

    @Schema(description = "Activity Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer status;

    @Schema(description = "Actual amount paid for the order，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "22354")
    private Integer totalPrice;

    @Schema(description = "Second kill inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer stock;

    @Schema(description = "Total inventory of flash sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer totalStock;

    @Schema(description = "Number of new orders", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer orderCount;

    @Schema(description = "Number of people paying", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer userCount;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== Product Field ==========

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of name Read
            example = "618Big Sale")
    private String spuName;
    @Schema(description = "Product Main Picture", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of picUrl Read
            example = "https://www.econets.cn/xx.png")
    private String picUrl;
    @Schema(description = "Commodity market price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of marketPrice Read
            example = "50")
    private Integer marketPrice;

}
