package cn.econets.blossom.module.product.api.sku;

import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuUpdateStockReqDTO;

import java.util.Collection;
import java.util.List;

/**
 * Products SKU API Interface
 *
 * 
 */
public interface ProductSkuApi {

    /**
     * Query SKU Information
     *
     * @param id SKU Number
     * @return SKU Information
     */
    ProductSkuRespDTO getSku(Long id);

    /**
     * Batch query SKU Array
     *
     * @param ids SKU Numbered list
     * @return SKU Array
     */
    List<ProductSkuRespDTO> getSkuList(Collection<Long> ids);

    /**
     * Batch query SKU Array
     *
     * @param spuIds SPU Numbered list
     * @return SKU Array
     */
    List<ProductSkuRespDTO> getSkuListBySpuId(Collection<Long> spuIds);

    /**
     * Update SKU Inventory（Increase or Reduce）
     *
     * @param updateStockReqDTO Update request
     */
    void updateSkuStock(ProductSkuUpdateStockReqDTO updateStockReqDTO);

}
