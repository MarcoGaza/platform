package cn.econets.blossom.module.promotion.api.discount;

import cn.econets.blossom.module.promotion.api.discount.dto.DiscountProductRespDTO;

import java.util.Collection;
import java.util.List;

/**
 * Limited time discount API Interface
 *
 */
public interface DiscountActivityApi {

    /**
     * Get the limited-time discount information of matching products
     *
     * @param skuIds Products SKU Number array
     * @return Limited time discount information
     */
    List<DiscountProductRespDTO> getMatchDiscountProductList(Collection<Long> skuIds);

}
