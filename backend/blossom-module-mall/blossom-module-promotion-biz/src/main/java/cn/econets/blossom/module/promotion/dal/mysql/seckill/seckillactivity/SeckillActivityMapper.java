package cn.econets.blossom.module.promotion.dal.mysql.seckill.seckillactivity;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity.SeckillActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity.AppSeckillActivityPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillActivityDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Second-sale event Mapper
 *
 */
@Mapper
public interface SeckillActivityMapper extends BaseMapperX<SeckillActivityDO> {

    default PageResult<SeckillActivityDO> selectPage(SeckillActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SeckillActivityDO>()
                .likeIfPresent(SeckillActivityDO::getName, reqVO.getName())
                .eqIfPresent(SeckillActivityDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SeckillActivityDO::getCreateTime, reqVO.getCreateTime())
                .apply(ObjectUtil.isNotNull(reqVO.getConfigId()), "FIND_IN_SET(" + reqVO.getConfigId() + ", config_ids) > 0")
                .orderByDesc(SeckillActivityDO::getId));
    }

    default List<SeckillActivityDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<SeckillActivityDO>()
                .eqIfPresent(SeckillActivityDO::getStatus, status));
    }

    /**
     * Update activity inventory(Reduce)
     *
     * @param id    Activity number
     * @param count Deducted inventory quantity(Positive number)
     * @return Number of affected rows
     */
    default int updateStockDecr(Long id, int count) {
        Assert.isTrue(count > 0);
        return update(null, new LambdaUpdateWrapper<SeckillActivityDO>()
                .eq(SeckillActivityDO::getId, id)
                .gt(SeckillActivityDO::getStock, count)
                .setSql("stock = stock - " + count));
    }

    /**
     * Update activity inventory（Increase）
     *
     * @param id    Activity number
     * @param count Increased inventory quantity(Positive number)
     * @return Number of affected rows
     */
    default int updateStockIncr(Long id, int count) {
        Assert.isTrue(count > 0);
        return update(null, new LambdaUpdateWrapper<SeckillActivityDO>()
                .eq(SeckillActivityDO::getId, id)
                .setSql("stock = stock + " + count));
    }

    default PageResult<SeckillActivityDO> selectPage(AppSeckillActivityPageReqVO pageReqVO, Integer status) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<SeckillActivityDO>()
                .eqIfPresent(SeckillActivityDO::getStatus, status)
                // TODO Yes find in set Thoughts；
                .apply(ObjectUtil.isNotNull(pageReqVO.getConfigId()), "FIND_IN_SET(" + pageReqVO.getConfigId() + ",config_ids) > 0"));
    }

    /**
     * Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple ones，One spuId Corresponding to a recent activity number
     *
     * @param spuIds spu Number
     * @param status Status
     * @return Include spuId Japanese activityId of map Object list
     */
    default List<Map<String, Object>> selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(@Param("spuIds") Collection<Long> spuIds, @Param("status") Integer status) {
        return selectMaps(new QueryWrapper<SeckillActivityDO>()
                .select("spu_id AS spuId, MAX(DISTINCT(id)) AS activityId") // The longer the time is id The bigger it gets Use directly id
                .in("spu_id", spuIds)
                .eq("status", status)
                .groupBy("spu_id"));
    }

    /**
     * Get the activity list of the specified activity number and
     * The start time and end time are less than the given time dateTime Activity list
     *
     * @param ids      Activity number
     * @param dateTime Specify date
     * @return Activity List
     */
    default List<SeckillActivityDO> selectListByIdsAndDateTimeLt(Collection<Long> ids, LocalDateTime dateTime) {
        return selectList(new LambdaQueryWrapperX<SeckillActivityDO>()
                .in(SeckillActivityDO::getId, ids)
                .lt(SeckillActivityDO::getStartTime, dateTime)
                .gt(SeckillActivityDO::getEndTime, dateTime)// Start time < Specify time < End time，That is, get the activities in the specified time period
                .orderByDesc(SeckillActivityDO::getCreateTime));
    }

}
