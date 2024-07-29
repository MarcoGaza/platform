package cn.econets.blossom.module.product.service.favorite;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.favorite.vo.ProductFavoritePageReqVO;
import cn.econets.blossom.module.product.controller.app.favorite.vo.AppFavoritePageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.favorite.ProductFavoriteDO;

import javax.validation.Valid;

/**
 * Product Collection Service Interface
 *
 */
public interface ProductFavoriteService {

    /**
     * Create product collection
     *
     * @param userId User Number
     * @param spuId SPU Number
     */
    Long createFavorite(Long userId, Long spuId);

    /**
     * Cancel product collection
     *
     * @param userId User Number
     * @param spuId SPU Number
     */
    void deleteFavorite(Long userId, Long spuId);

    /**
     * Query user favorites list by page
     *
     * @param userId User Number
     * @param reqVO Request vo
     */
    PageResult<ProductFavoriteDO> getFavoritePage(Long userId, @Valid AppFavoritePageReqVO reqVO);

    /**
     * Query user favorites list by page
     *
     * @param reqVO Request vo
     */
    PageResult<ProductFavoriteDO> getFavoritePage(@Valid ProductFavoritePageReqVO reqVO);

    /**
     * Get the collected items
     *
     * @param userId User Number
     * @param spuId SPU Number
     */
    ProductFavoriteDO getFavorite(Long userId, Long spuId);

    /**
     * Get the number of user favorites
     *
     * @param userId User Number
     * @return Quantity
     */
    Long getFavoriteCount(Long userId);

}
