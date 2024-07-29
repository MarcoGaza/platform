package cn.econets.blossom.module.product.service.history;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.history.vo.ProductBrowseHistoryPageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.history.ProductBrowseHistoryDO;
import cn.econets.blossom.module.product.dal.mysql.history.ProductBrowseHistoryMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Product browsing history Service Implementation class
 *
 */
@Service
@Validated
public class ProductBrowseHistoryServiceImpl implements ProductBrowseHistoryService {

    private static final int USER_STORE_MAXIMUM = 100;

    @Resource
    private ProductBrowseHistoryMapper browseHistoryMapper;

    @Override
    public void createBrowseHistory(Long userId, Long spuId) {
        // Do not record when the user is not logged in
        if (userId == null) {
            return;
        }

        // Situation 1：The same product，Only keep the latest record
        ProductBrowseHistoryDO history = browseHistoryMapper.selectByUserIdAndSpuId(userId, spuId);
        if (history != null) {
            browseHistoryMapper.deleteById(history);
        } else {
            // Situation 2：Limit the number of browsing records for each user（Only check the earliest record、Total number of records）
            // TODO It is best to check the quantity here first。If it is found that it exceeds，Delete again；Main consideration，Some may not exceed，One more time in advance sql Query
            Page<ProductBrowseHistoryDO> pageResult = browseHistoryMapper.selectPageByUserIdOrderByCreateTimeAsc(userId, 1, 1);
            if (pageResult.getTotal() >= USER_STORE_MAXIMUM) {
                browseHistoryMapper.deleteById(CollUtil.getFirst(pageResult.getRecords()));
            }
        }

        // Insert
        ProductBrowseHistoryDO browseHistory = new ProductBrowseHistoryDO()
                .setUserId(userId)
                .setSpuId(spuId);
        browseHistoryMapper.insert(browseHistory);
    }

    @Override
    public void hideUserBrowseHistory(Long userId, Collection<Long> spuIds) {
        browseHistoryMapper.updateUserDeletedByUserId(userId, spuIds, true);
    }

    @Override
    public PageResult<ProductBrowseHistoryDO> getBrowseHistoryPage(ProductBrowseHistoryPageReqVO pageReqVO) {
        return browseHistoryMapper.selectPage(pageReqVO);
    }

}