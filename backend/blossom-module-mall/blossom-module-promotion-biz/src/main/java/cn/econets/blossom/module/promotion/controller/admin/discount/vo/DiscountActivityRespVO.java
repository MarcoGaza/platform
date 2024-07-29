package cn.econets.blossom.module.promotion.controller.admin.discount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Limited time discount event Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscountActivityRespVO extends DiscountActivityBaseVO {

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Activity Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Activity status cannot be empty")
    private Integer status;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;


    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048") // TODO Between attributes，Only one line can be left blank；
    private Long spuId;

    @Schema(description = "Limited time discount items", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Product> products;

    // ========== Product Field ==========

    // TODO A promotional activity，Will be associated with multiple products，So it doesn't need to return spuName Ha；
    // TODO The final interface displays the fields：Number、Activity Name、Number of participating products、Activity Status、Start time、End time、Operation
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
