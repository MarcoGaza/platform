package cn.econets.blossom.module.product.service.spu;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuPageReqVO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuSaveReqVO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuUpdateStatusReqVO;
import cn.econets.blossom.module.product.controller.app.spu.vo.AppProductSpuPageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import org.springframework.scheduling.annotation.Async;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Products SPU Service Interface
 *
 */
public interface ProductSpuService {

    /**
     * Create product SPU
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSpu(@Valid ProductSpuSaveReqVO createReqVO);

    /**
     * Update product SPU
     *
     * @param updateReqVO Update information
     */
    void updateSpu(@Valid ProductSpuSaveReqVO updateReqVO);

    /**
     * Delete product SPU
     *
     * @param id Number
     */
    void deleteSpu(Long id);

    /**
     * Get the product SPU
     *
     * @param id Number
     * @return Products SPU
     */
    ProductSpuDO getSpu(Long id);

    /**
     * Get goods SPU List
     *
     * @param ids Number array
     * @return Products SPU List
     */
    List<ProductSpuDO> getSpuList(Collection<Long> ids);

    /**
     * Get the product in the specified state SPU List
     *
     * @param status Status
     * @return Products SPU List
     */
    List<ProductSpuDO> getSpuListByStatus(Integer status);

    /**
     * Get goods SPU Pagination，Provided for use by the backend of Guanilan
     *
     * @param pageReqVO Paged query
     * @return ProductsspuPagination
     */
    PageResult<ProductSpuDO> getSpuPage(ProductSpuPageReqVO pageReqVO);

    /**
     * Get the product SPU Pagination，Provided to users App Use
     *
     * @param pageReqVO Paged query
     * @return Products SPU Pagination
     */
    PageResult<ProductSpuDO> getSpuPage(AppProductSpuPageReqVO pageReqVO);

    /**
     * Update product SPU Inventory（Increment）
     *
     * @param stockIncrCounts SPU Number and inventory changes（Increment）Mapping
     */
    void updateSpuStock(Map<Long, Integer> stockIncrCounts);

    /**
     * Update SPU Status
     *
     * @param updateReqVO Update request
     */
    void updateSpuStatus(ProductSpuUpdateStatusReqVO updateReqVO);

    /**
     * Get SPU List label corresponding to Count Quantity
     *
     * @return Count Quantity
     */
    Map<Integer, Long> getTabsCount();

    /**
     * By category categoryId Query SPU Number
     *
     * @param categoryId Category categoryId
     * @return SPU Quantity
     */
    Long getSpuCountByCategoryId(Long categoryId);


    /**
     * Check if the product is valid。As follows，Deemed invalid：
     * 1. Product number does not exist
     * 2. The product is disabled
     *
     * @param ids Product number array
     * @return Products SPU List
     */
    List<ProductSpuDO> validateSpuList(Collection<Long> ids);

    /**
     * Update product SPU Views
     *
     * @param id        Products SPU Number
     * @param incrCount Increased quantity
     */
    @Async
    void updateBrowseCount(Long id, int incrCount);

}
