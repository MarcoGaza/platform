package cn.econets.blossom.module.promotion.controller.admin.discount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Management Backend - Limited time discount event creation Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscountActivityCreateReqVO extends DiscountActivityBaseVO {

    /**
     * Product List
     */
    @NotEmpty(message = "The product list cannot be empty")
    @Valid
    private List<Product> products;

}
