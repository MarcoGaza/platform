package cn.econets.blossom.module.promotion.dal.mysql.combination;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Group buying activity Mapper
 *
 */
@Mapper
public interface CombinationActivityMapper extends BaseMapperX<CombinationActivityDO> {

    default PageResult<CombinationActivityDO> selectPage(CombinationActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CombinationActivityDO>()
                .likeIfPresent(CombinationActivityDO::getName, reqVO.getName())
                .eqIfPresent(CombinationActivityDO::getStatus, reqVO.getStatus())
                .orderByDesc(CombinationActivityDO::getId));
    }

    default List<CombinationActivityDO> selectListByStatus(Integer status) {
        return selectList(CombinationActivityDO::getStatus, status);
    }

    default PageResult<CombinationActivityDO> selectPage(PageParam pageParam, Integer status) {
        return selectPage(pageParam, new LambdaQueryWrapperX<CombinationActivityDO>()
                .eq(CombinationActivityDO::getStatus, status));
    }

    default List<CombinationActivityDO> selectListByStatus(Integer status, Integer count) {
        return selectList(new LambdaQueryWrapperX<CombinationActivityDO>()
                .eq(CombinationActivityDO::getStatus, status)
                .last("LIMIT " + count));
    }

    /**
     * Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple ones，One spuId Corresponding to a recent activity number
     * @param spuIds spu Number
     * @param status Status
     * @return Include spuId Japanese activityId of map Object list
     */
    default List<Map<String, Object>> selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(@Param("spuIds") Collection<Long> spuIds, @Param("status") Integer status) {
        return selectMaps(new QueryWrapper<CombinationActivityDO>()
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
    default List<CombinationActivityDO> selectListByIdsAndDateTimeLt(Collection<Long> ids, LocalDateTime dateTime) {
        return selectList(new LambdaQueryWrapperX<CombinationActivityDO>()
                .in(CombinationActivityDO::getId, ids)
                .lt(CombinationActivityDO::getStartTime, dateTime)
                .gt(CombinationActivityDO::getEndTime, dateTime)// Start time < Specify time < End time，That is, get the activities in the specified time period
                .orderByDesc(CombinationActivityDO::getCreateTime));
    }

}
