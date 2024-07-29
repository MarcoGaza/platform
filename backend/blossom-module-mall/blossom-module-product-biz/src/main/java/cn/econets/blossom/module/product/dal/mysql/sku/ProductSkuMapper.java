package cn.econets.blossom.module.product.dal.mysql.sku;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ProductSkuMapper extends BaseMapperX<ProductSkuDO> {

    default List<ProductSkuDO> selectListBySpuId(Long spuId) {
        return selectList(ProductSkuDO::getSpuId, spuId);
    }

    default List<ProductSkuDO> selectListBySpuId(Collection<Long> spuIds) {
        return selectList(ProductSkuDO::getSpuId, spuIds);
    }

    default void deleteBySpuId(Long spuId) {
        delete(new LambdaQueryWrapperX<ProductSkuDO>().eq(ProductSkuDO::getSpuId, spuId));
    }

    /**
     * Update SKU Inventory（Increase）
     *
     * @param id        Number
     * @param incrCount Increase inventory（Positive number）
     */
    default void updateStockIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount > 0);
        LambdaUpdateWrapper<ProductSkuDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<ProductSkuDO>()
                .setSql(" stock = stock + " + incrCount)
                .eq(ProductSkuDO::getId, id);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * Update SKU Inventory（Reduce）
     *
     * @param id        Number
     * @param incrCount Reduce inventory（Negative number）
     * @return Update number of entries
     */
    default int updateStockDecr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount < 0);
        LambdaUpdateWrapper<ProductSkuDO> updateWrapper = new LambdaUpdateWrapper<ProductSkuDO>()
                .setSql(" stock = stock + " + incrCount) // Negative number，So use + Number
                .eq(ProductSkuDO::getId, id)
                .ge(ProductSkuDO::getStock, -incrCount); // cas Logic
        return update(null, updateWrapper);
    }

}
