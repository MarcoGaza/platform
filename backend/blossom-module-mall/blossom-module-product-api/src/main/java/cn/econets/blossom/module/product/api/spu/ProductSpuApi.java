package cn.econets.blossom.module.product.api.spu;

import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;

import java.util.Collection;
import java.util.List;

/**
 * Products SPU API Interface
 *
 * 
 */
public interface ProductSpuApi {

    /**
     * Batch query SPU Array
     *
     * @param ids SPU Numbered list
     * @return SPU Array
     */
    List<ProductSpuRespDTO> getSpuList(Collection<Long> ids);

    /**
     * Batch query SPU Array，And verify whether SPU Is it valid?。
     *
     * The following situation，Deemed invalid：
     * 1. Product number does not exist
     * 2. Product is disabled
     *
     * @param ids SPU Numbered list
     * @return SPU Array
     */
    List<ProductSpuRespDTO> validateSpuList(Collection<Long> ids);

    /**
     * Get SPU
     *
     * @return SPU
     */
    ProductSpuRespDTO getSpu(Long id);

}
