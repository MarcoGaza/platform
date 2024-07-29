package cn.econets.blossom.module.product.dal.mysql.spu;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuPageReqVO;
import cn.econets.blossom.module.product.controller.app.spu.vo.AppProductSpuPageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.econets.blossom.module.product.enums.ProductConstants;
import cn.econets.blossom.module.product.enums.spu.ProductSpuStatusEnum;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Objects;
import java.util.Set;

@Mapper
public interface ProductSpuMapper extends BaseMapperX<ProductSpuDO> {

    /**
     * Get the product SPU Paged list data
     *
     * @param reqVO Paging request parameters
     * @return Products SPU Paged list data
     */
    default PageResult<ProductSpuDO> selectPage(ProductSpuPageReqVO reqVO) {
        Integer tabType = reqVO.getTabType();
        LambdaQueryWrapperX<ProductSpuDO> queryWrapper = new LambdaQueryWrapperX<ProductSpuDO>()
                .likeIfPresent(ProductSpuDO::getName, reqVO.getName())
                .eqIfPresent(ProductSpuDO::getCategoryId, reqVO.getCategoryId())
                .betweenIfPresent(ProductSpuDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductSpuDO::getSort);
        appendTabQuery(tabType, queryWrapper);
        return selectPage(reqVO, queryWrapper);
    }

    /**
     * Query the inventory that triggered the alert SPU Quantity
     *
     * @return Triggering the alert inventory SPU Quantity
     */
    default Long selectCount() {
        LambdaQueryWrapperX<ProductSpuDO> queryWrapper = new LambdaQueryWrapperX<>();
        // Inventory is less than or equal to warning inventory
        queryWrapper.le(ProductSpuDO::getStock, ProductConstants.ALERT_STOCK)
                // If the inventory triggers the alert inventory and the status is recycle bin, it will not be counted in the number of triggered alert inventory
                .notIn(ProductSpuDO::getStatus, ProductSpuStatusEnum.RECYCLE.getStatus());
        return selectCount(queryWrapper);
    }

    /**
     * Get goods SPU Pagination，Provided to users App Use
     */
    default PageResult<ProductSpuDO> selectPage(AppProductSpuPageReqVO pageReqVO, Set<Long> categoryIds) {
        LambdaQueryWrapperX<ProductSpuDO> query = new LambdaQueryWrapperX<ProductSpuDO>()
                // Keyword matching，Currently only matches product names
                .likeIfPresent(ProductSpuDO::getName, pageReqVO.getKeyword())
                // Category
                .inIfPresent(ProductSpuDO::getCategoryId, categoryIds);
        // Listing Status and in stock
        query.eq(ProductSpuDO::getStatus, ProductSpuStatusEnum.ENABLE.getStatus());

        // Sorting logic
        if (Objects.equals(pageReqVO.getSortField(), AppProductSpuPageReqVO.SORT_FIELD_SALES_COUNT)) {
            query.last(String.format(" ORDER BY (sales_count + virtual_sales_count) %s, sort DESC, id DESC",
                    pageReqVO.getSortAsc() ? "ASC" : "DESC"));
        } else if (Objects.equals(pageReqVO.getSortField(), AppProductSpuPageReqVO.SORT_FIELD_PRICE)) {
            query.orderBy(true, pageReqVO.getSortAsc(), ProductSpuDO::getPrice)
                    .orderByDesc(ProductSpuDO::getSort).orderByDesc(ProductSpuDO::getId);
        } else if (Objects.equals(pageReqVO.getSortField(), AppProductSpuPageReqVO.SORT_FIELD_CREATE_TIME)) {
            query.orderBy(true, pageReqVO.getSortAsc(), ProductSpuDO::getCreateTime)
                    .orderByDesc(ProductSpuDO::getSort).orderByDesc(ProductSpuDO::getId);
        } else {
            query.orderByDesc(ProductSpuDO::getSort).orderByDesc(ProductSpuDO::getId);
        }
        return selectPage(pageReqVO, query);
    }

    /**
     * Update product SPU Inventory
     *
     * @param id        Products SPU Number
     * @param incrCount Increased inventory quantity
     */
    default void updateStock(Long id, Integer incrCount) {
        LambdaUpdateWrapper<ProductSpuDO> updateWrapper = new LambdaUpdateWrapper<ProductSpuDO>()
                // Negative number，So use + Number
                .setSql(" stock = stock +" + incrCount)
                .eq(ProductSpuDO::getId, id);
        update(null, updateWrapper);
    }

    /**
     * Add background Tab Option query conditions
     *
     * @param tabType Tag type
     * @param query   Query conditions
     */
    static void appendTabQuery(Integer tabType, LambdaQueryWrapperX<ProductSpuDO> query) {
        // Items for sale
        if (ObjectUtil.equals(ProductSpuPageReqVO.FOR_SALE, tabType)) {
            query.eqIfPresent(ProductSpuDO::getStatus, ProductSpuStatusEnum.ENABLE.getStatus());
        }
        // Goods in storage
        if (ObjectUtil.equals(ProductSpuPageReqVO.IN_WAREHOUSE, tabType)) {
            query.eqIfPresent(ProductSpuDO::getStatus, ProductSpuStatusEnum.DISABLE.getStatus());
        }
        // Sold out product
        if (ObjectUtil.equals(ProductSpuPageReqVO.SOLD_OUT, tabType)) {
            query.eqIfPresent(ProductSpuDO::getStock, 0);
        }
        // Alert Inventory
        if (ObjectUtil.equals(ProductSpuPageReqVO.ALERT_STOCK, tabType)) {
            query.le(ProductSpuDO::getStock, ProductConstants.ALERT_STOCK)
                    // If the inventory triggers the alert inventory and the status is recycle bin, it will not be displayed in the alert inventory list
                    .notIn(ProductSpuDO::getStatus, ProductSpuStatusEnum.RECYCLE.getStatus());
        }
        // Recycle Bin
        if (ObjectUtil.equals(ProductSpuPageReqVO.RECYCLE_BIN, tabType)) {
            query.eqIfPresent(ProductSpuDO::getStatus, ProductSpuStatusEnum.RECYCLE.getStatus());
        }
    }

    /**
     * Update product SPU Views
     *
     * @param id        Products SPU Number
     * @param incrCount Increased quantity
     */
    default void updateBrowseCount(Long id, int incrCount) {
        LambdaUpdateWrapper<ProductSpuDO> updateWrapper = new LambdaUpdateWrapper<ProductSpuDO>()
                .setSql(" browse_count = browse_count +" + incrCount)
                .eq(ProductSpuDO::getId, id);
        update(null, updateWrapper);
    }

}
