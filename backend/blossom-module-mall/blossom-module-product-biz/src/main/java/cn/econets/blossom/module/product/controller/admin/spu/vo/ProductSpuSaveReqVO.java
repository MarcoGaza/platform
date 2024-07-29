package cn.econets.blossom.module.product.controller.admin.spu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Products SPU Newly added/Update Request VO")
@Data
public class ProductSpuSaveReqVO {

    @Schema(description = "Product Number", example = "1")
    private Long id;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool short sleeves")
    @NotEmpty(message = "Product name cannot be empty")
    private String name;

    @Schema(description = "Keywords", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool and smooth without sweating")
    @NotEmpty(message = "Product keywords cannot be empty")
    private String keyword;

    @Schema(description = "Product Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "Introduction to cool short-sleeved shirts")
    @NotEmpty(message = "Product description cannot be empty")
    private String introduction;

    @Schema(description = "Product details", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool short-sleeved shirt details")
    @NotEmpty(message = "Product details cannot be empty")
    private String description;

    @Schema(description = "Product Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product category cannot be empty")
    private Long categoryId;

    @Schema(description = "Product brand number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product brand cannot be empty")
    private Long brandId;

    @Schema(description = "Product cover image", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    @NotEmpty(message = "The product cover image cannot be empty")
    private String picUrl;

    @Schema(description = "Product Carousel", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "[https://www.econets.cn/xx.png, https://www.econets.cn/xxx.png]")
    private List<String> sliderPicUrls;

    @Schema(description = "Sort Field", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product sorting field cannot be empty")
    private Integer sort;

    // ========== SKU Related fields =========

    @Schema(description = "Specification type", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Product specification type cannot be empty")
    private Boolean specType;

    // ========== Logistics related fields =========

    @Schema(description = "Shipping method array", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "The delivery method cannot be empty")
    private List<Integer> deliveryTypes;

    @Schema(description = "Logistics configuration template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    private Long deliveryTemplateId;

    // ========== Marketing related fields =========

    @Schema(description = "Send points", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    @NotNull(message = "The product gift points cannot be empty")
    private Integer giveIntegral;

    @Schema(description = "Distribution Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Product distribution type cannot be empty")
    private Boolean subCommissionType;

    // ========== Statistics related fields =========

    @Schema(description = "Virtual sales", example = "66")
    private Integer virtualSalesCount;

    @Schema(description = "Product Sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "1999")
    private Integer salesCount;

    @Schema(description = "Views", requiredMode = Schema.RequiredMode.REQUIRED, example = "1999")
    private Integer browseCount;

    // ========== SKU Related fields =========

    @Schema(description = "SKU Array")
    @Valid
    private List<ProductSkuSaveReqVO> skus;

}
