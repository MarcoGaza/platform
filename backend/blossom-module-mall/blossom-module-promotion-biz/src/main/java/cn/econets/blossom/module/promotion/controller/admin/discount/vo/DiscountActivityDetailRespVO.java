package cn.econets.blossom.module.promotion.controller.admin.discount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "Management Backend - Details of limited time discount event Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscountActivityDetailRespVO extends DiscountActivityRespVO {

    /**
     * Product List
     */
    private List<Product> products;

}
