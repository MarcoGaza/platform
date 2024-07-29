package cn.econets.blossom.module.crm.service.business.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Update business opportunity products Update Req BO
 *
 */
@Data
public class CrmBusinessUpdateProductReqBO {

    /**
     * Opportunity Number
     */
    @NotNull(message = "Opportunity number cannot be empty")
    private Long id;

    // TODO @Think again
    @NotEmpty(message = "The product list cannot be empty")
    private List<CrmBusinessProductItem> productItems;

    @Schema(description = "Product List")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrmBusinessProductItem {

        @Schema(description = "Product Number", example = "20529")
        @NotNull(message = "Product number cannot be empty")
        private Long id;

        @Schema(description = "Product quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "8911")
        @NotNull(message = "Product quantity cannot be empty")
        private Integer count;

        @Schema(description = "Product discount")
        private Integer discountPercent;

    }

}
