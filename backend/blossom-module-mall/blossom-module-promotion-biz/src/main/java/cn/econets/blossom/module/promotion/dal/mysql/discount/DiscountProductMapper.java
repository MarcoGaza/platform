package cn.econets.blossom.module.promotion.dal.mysql.discount;

import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountProductDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Limited Time Discount Mall Mapper
 *
 */
@Mapper
public interface DiscountProductMapper extends BaseMapperX<DiscountProductDO> {

    default List<DiscountProductDO> selectListBySkuId(Collection<Long> skuIds) {
        return selectList(DiscountProductDO::getSkuId, skuIds);
    }

    default List<DiscountProductDO> selectListByActivityId(Long activityId) {
        return selectList(DiscountProductDO::getActivityId, activityId);
    }

    default List<DiscountProductDO> selectListByActivityId(Collection<Long> activityIds) {
        return selectList(DiscountProductDO::getActivityId, activityIds);
    }

    // TODO Logic，Try to avoid writing join Sentence Ha，You can take a look at this query，Is there any way to optimize it?？A current idea，Yes 2 Query，Performance is also ok of
    List<DiscountProductDO> getMatchDiscountProductList(@Param("skuIds") Collection<Long> skuIds);

    /**
     * Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple words，One spuId Corresponding to a recent activity number
     *
     * @param spuIds spu Number
     * @param status Status
     * @return Include spuId Japanese activityId of map Object list
     */
    default List<Map<String, Object>> selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(Collection<Long> spuIds, Integer status) {
        return selectMaps(new QueryWrapper<DiscountProductDO>()
                .select("spu_id AS spuId, MAX(DISTINCT(activity_id)) AS activityId")
                .in("spu_id", spuIds)
                .eq("activity_status", status)
                .groupBy("spu_id"));
    }

}
