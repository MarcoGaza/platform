package cn.econets.blossom.module.product.api.category;

import java.util.Collection;

/**
 * Product Categories API Interface
 *
 */
public interface ProductCategoryApi {

    /**
     * Check whether the product category is valid。The following situations，Deemed invalid：
     * 1. Product category number does not exist
     * 2. Product category is disabled
     *
     * @param ids Product category number array
     */
    void validateCategoryList(Collection<Long> ids);
}
