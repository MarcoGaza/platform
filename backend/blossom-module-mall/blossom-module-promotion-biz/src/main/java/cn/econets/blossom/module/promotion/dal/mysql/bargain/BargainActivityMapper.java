package cn.econets.blossom.module.promotion.dal.mysql.bargain;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Bargaining activity Mapper
 *
 */
@Mapper
public interface BargainActivityMapper extends BaseMapperX<BargainActivityDO> {

    default PageResult<BargainActivityDO> selectPage(BargainActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BargainActivityDO>()
                .likeIfPresent(BargainActivityDO::getName, reqVO.getName())
                .eqIfPresent(BargainActivityDO::getStatus, reqVO.getStatus())
                .orderByDesc(BargainActivityDO::getId));
    }

    default List<BargainActivityDO> selectListByStatus(Integer status) {
        return selectList(BargainActivityDO::getStatus, status);
    }

    /**
     * Update activity inventory
     *
     * @param id    Activity number
     * @param count Deducted inventory quantity
     * @return Number of affected rows
     */
    default int updateStock(Long id, int count) {
        // Situation 1：Increase inventory
        if (count > 0) {
            return update(null, new LambdaUpdateWrapper<BargainActivityDO>()
                    .eq(BargainActivityDO::getId, id)
                    .setSql("stock = stock + " + count));
        }
        // Case 2：Deduct inventory
        count = -count; // Take the positive
        return update(null, new LambdaUpdateWrapper<BargainActivityDO>()
                .eq(BargainActivityDO::getId, id)
                .ge(BargainActivityDO::getStock, count)
                .setSql("stock = stock - " + count));
    }

    /**
     * Query location now Date and time status Activity page for status
     *
     * @param pageReqVO Paging parameters
     * @param status    Status
     * @param now       Current date and time
     * @return Activity paging
     */
    default PageResult<BargainActivityDO> selectPage(PageParam pageReqVO, Integer status, LocalDateTime now) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<BargainActivityDO>()
                .eq(BargainActivityDO::getStatus, status)
                .le(BargainActivityDO::getStartTime, now)
                .ge(BargainActivityDO::getEndTime, now));
    }

    /**
     * Query location now Date and time status Activity page for status
     *
     * @param status Status
     * @param now    Current date and time
     * @return Activity paging
     */
    default List<BargainActivityDO> selectList(Integer count, Integer status, LocalDateTime now) {
        return selectList(new LambdaQueryWrapperX<BargainActivityDO>()
                .eq(BargainActivityDO::getStatus, status)
                .le(BargainActivityDO::getStartTime, now)
                .ge(BargainActivityDO::getEndTime, now)
                .last("LIMIT " + count));
    }

    /**
     * Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple ones，One spuId Corresponding to the most recent activity number
     *
     * @param spuIds spu Number
     * @param status Status
     * @return Include spuId Japanese activityId of map Object list
     */
    default List<Map<String, Object>> selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(Collection<Long> spuIds, Integer status) {
        return selectMaps(new QueryWrapper<BargainActivityDO>()
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
    default List<BargainActivityDO> selectListByIdsAndDateTimeLt(Collection<Long> ids, LocalDateTime dateTime) {
        return selectList(new LambdaQueryWrapperX<BargainActivityDO>()
                .in(BargainActivityDO::getId, ids)
                .lt(BargainActivityDO::getStartTime, dateTime)
                .gt(BargainActivityDO::getEndTime, dateTime)// Start time < Specify time < End time，That is, get the activities in the specified time period
                .orderByDesc(BargainActivityDO::getCreateTime));
    }

}
