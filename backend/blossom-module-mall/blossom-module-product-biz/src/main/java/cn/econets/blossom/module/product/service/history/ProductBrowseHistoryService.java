package cn.econets.blossom.module.product.service.history;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.history.vo.ProductBrowseHistoryPageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.history.ProductBrowseHistoryDO;
import org.springframework.scheduling.annotation.Async;

import java.util.Collection;

/**
 * Product browsing history Service Interface
 *
 */
public interface ProductBrowseHistoryService {

    /**
     * Create product browsing record
     *
     * @param userId User Number
     * @param spuId  SPU Number
     */
    @Async
    void createBrowseHistory(Long userId, Long spuId);

    /**
     * Hide user product browsing history
     *
     * @param userId User Number
     * @param spuId  SPU Number
     */
    void hideUserBrowseHistory(Long userId, Collection<Long> spuId);

    /**
     * Get product browsing history page
     *
     * @param pageReqVO Paged query
     * @return Product browsing history paging
     */
    PageResult<ProductBrowseHistoryDO> getBrowseHistoryPage(ProductBrowseHistoryPageReqVO pageReqVO);

}