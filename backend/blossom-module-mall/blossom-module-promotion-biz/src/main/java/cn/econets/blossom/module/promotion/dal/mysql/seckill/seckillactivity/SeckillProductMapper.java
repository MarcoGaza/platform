package cn.econets.blossom.module.promotion.dal.mysql.seckill.seckillactivity;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillProductDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Second-sale products Mapper
 *
 */
@Mapper
public interface SeckillProductMapper extends BaseMapperX<SeckillProductDO> {

    default List<SeckillProductDO> selectListByActivityId(Long activityId) {
        return selectList(SeckillProductDO::getActivityId, activityId);
    }

    default SeckillProductDO selectByActivityIdAndSkuId(Long activityId, Long skuId) {
        return selectOne(SeckillProductDO::getActivityId, activityId,
                SeckillProductDO::getSkuId, skuId);
    }

    default List<SeckillProductDO> selectListByActivityId(Collection<Long> ids) {
        return selectList(SeckillProductDO::getActivityId, ids);
    }

    /**
     * Update activity inventory（Reduce）
     *
     * @param id    Activity number
     * @param count Deducted inventory quantity(Reduce inventory)
     * @return Number of affected rows
     */
    default int updateStockDecr(Long id, int count) {
        Assert.isTrue(count > 0);
        return update(null, new LambdaUpdateWrapper<SeckillProductDO>()
                .eq(SeckillProductDO::getId, id)
                .ge(SeckillProductDO::getStock, count)
                .setSql("stock = stock - " + count));
    }

    /**
     * Update activity inventory（Increase）
     *
     * @param id    Activity number
     * @param count Inventory needs to be increased（Increase inventory）
     * @return Number of affected rows
     */
    default int updateStockIncr(Long id, int count) {
        Assert.isTrue(count > 0);
        return update(null, new LambdaUpdateWrapper<SeckillProductDO>()
                .eq(SeckillProductDO::getId, id)
                .setSql("stock = stock + " + count));
    }

}
