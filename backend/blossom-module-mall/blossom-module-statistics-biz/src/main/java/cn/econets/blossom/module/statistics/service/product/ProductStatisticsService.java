package cn.econets.blossom.module.statistics.service.product;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.pojo.SortablePageParam;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.product.vo.ProductStatisticsReqVO;
import cn.econets.blossom.module.statistics.controller.admin.product.vo.ProductStatisticsRespVO;
import cn.econets.blossom.module.statistics.dal.dataobject.product.ProductStatisticsDO;

import java.util.List;

/**
 * Product Statistics Service Interface
 *
 */
public interface ProductStatisticsService {

    /**
     * Get the product statistics ranking page
     *
     * @param reqVO     Query conditions
     * @param pageParam Page sorting query
     * @return Product Statistics Paging
     */
    PageResult<ProductStatisticsDO> getProductStatisticsRankPage(ProductStatisticsReqVO reqVO, SortablePageParam pageParam);

    /**
     * Get statistical analysis of product status
     *
     * @param reqVO Query conditions
     * @return Statistical data comparison
     */
    DataComparisonRespVO<ProductStatisticsRespVO> getProductStatisticsAnalyse(ProductStatisticsReqVO reqVO);

    /**
     * Get product status details
     *
     * @param reqVO Query conditions
     * @return Statistical data comparison
     */
    List<ProductStatisticsDO> getProductStatisticsList(ProductStatisticsReqVO reqVO);

    /**
     * Count the product data for a specified number of days
     *
     * @return Statistical results
     */
    String statisticsProduct(Integer days);

}