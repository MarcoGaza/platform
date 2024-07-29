package cn.econets.blossom.module.product.service.sku;

import cn.econets.blossom.module.product.api.sku.dto.ProductSkuUpdateStockReqDTO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSkuSaveReqVO;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;

import java.util.Collection;
import java.util.List;

/**
 * Products SKU Service Interface
 *
 */
public interface ProductSkuService {

    /**
     * Delete product SKU
     *
     * @param id Number
     */
    void deleteSku(Long id);

    /**
     * Get goods SKU Information
     *
     * @param id Number
     * @return Products SKU Information
     */
    ProductSkuDO getSku(Long id);

    /**
     * Get goods SKU List
     *
     * @param ids Number
     * @return ProductsskuList
     */
    List<ProductSkuDO> getSkuList(Collection<Long> ids);

    /**
     * Yes sku The combination of attributes, etc., is verified for legality
     *
     * @param list skuCombined collection
     */
    void validateSkuList(List<ProductSkuSaveReqVO> list, Boolean specType);

    /**
     * Batch creation SKU
     *
     * @param spuId Products SPU Number
     * @param list  SKU Object collection
     */
    void createSkuList(Long spuId, List<ProductSkuSaveReqVO> list);

    /**
     * According to SPU Number，Batch update it SKU Information
     *
     * @param spuId SPU Encoding
     * @param skus  SKU Collection of
     */
    void updateSkuList(Long spuId, List<ProductSkuSaveReqVO> skus);

    /**
     * Update SKU Inventory（Increment）
     * <p>
     * If the updated inventory is insufficient，Will throw an exception
     *
     * @param updateStockReqDTO Change request
     */
    void updateSkuStock(ProductSkuUpdateStockReqDTO updateStockReqDTO);

    /**
     * Get goods SKU Collection
     *
     * @param spuId spu Number
     * @return Productssku Collection
     */
    List<ProductSkuDO> getSkuListBySpuId(Long spuId);

    /**
     * Get spu Corresponding SKU Collection
     *
     * @param spuIds spu Encoding set
     * @return Products sku Collection
     */
    List<ProductSkuDO> getSkuListBySpuId(Collection<Long> spuIds);

    /**
     * Passed spuId Delete sku Information
     *
     * @param spuId spu Encoding
     */
    void deleteSkuBySpuId(Long spuId);

    /**
     * Update sku Properties
     *
     * @param propertyId   Properties id
     * @param propertyName Attribute name
     * @return int Number of affected rows
     */
    int updateSkuProperty(Long propertyId, String propertyName);

    /**
     * Update sku Property value
     *
     * @param propertyValueId   Property value id
     * @param propertyValueName Attribute value name
     * @return int Number of affected rows
     */
    int updateSkuPropertyValue(Long propertyValueId, String propertyValueName);

}
