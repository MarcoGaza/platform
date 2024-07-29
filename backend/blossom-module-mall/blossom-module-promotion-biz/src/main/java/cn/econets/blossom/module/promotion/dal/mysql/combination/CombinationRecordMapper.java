package cn.econets.blossom.module.promotion.dal.mysql.combination;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordReqPageVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationRecordDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Group buying record Mapper
 *
 */
@Mapper
public interface CombinationRecordMapper extends BaseMapperX<CombinationRecordDO> {

    default CombinationRecordDO selectByUserIdAndOrderId(Long userId, Long orderId) {
        return selectOne(CombinationRecordDO::getUserId, userId,
                CombinationRecordDO::getOrderId, orderId);
    }

    /**
     * Query group purchase records
     *
     * @param headId Team Leader Number
     * @return Group buying record
     */
    default CombinationRecordDO selectByHeadId(Long headId, Integer status) {
        return selectOne(new LambdaQueryWrapperX<CombinationRecordDO>()
                .eq(CombinationRecordDO::getId, headId)
                .eq(CombinationRecordDO::getStatus, status));
    }

    /**
     * Query group purchase records
     *
     * @param userId     User id
     * @param activityId Activities id
     * @return Group buying record
     */
    default List<CombinationRecordDO> selectListByUserIdAndActivityId(Long userId, Long activityId) {
        return selectList(new LambdaQueryWrapperX<CombinationRecordDO>()
                .eq(CombinationRecordDO::getUserId, userId)
                .eq(CombinationRecordDO::getActivityId, activityId));
    }

    /**
     * Get the nearest count Data
     *
     * @param count Quantity
     * @return Group buying record list
     */
    default List<CombinationRecordDO> selectLatestList(int count) {
        return selectList(new LambdaQueryWrapperX<CombinationRecordDO>()
                .orderByDesc(CombinationRecordDO::getId)
                .last("LIMIT " + count));
    }

    default List<CombinationRecordDO> selectListByActivityIdAndStatusAndHeadId(Long activityId, Integer status,
                                                                               Long headId, Integer count) {
        return selectList(new LambdaQueryWrapperX<CombinationRecordDO>()
                .eqIfPresent(CombinationRecordDO::getActivityId, activityId)
                .eqIfPresent(CombinationRecordDO::getStatus, status)
                .eq(CombinationRecordDO::getHeadId, headId)
                .orderByDesc(CombinationRecordDO::getId)
                .last("LIMIT " + count));
    }

    default Map<Long, Integer> selectCombinationRecordCountMapByActivityIdAndStatusAndHeadId(Collection<Long> activityIds,
                                                                                             Integer status, Long headId) {
        // SQL count Query
        List<Map<String, Object>> result = selectMaps(new QueryWrapper<CombinationRecordDO>()
                .select("COUNT(DISTINCT(user_id)) AS recordCount, activity_id AS activityId")
                .in("activity_id", activityIds)
                .eq(status != null, "status", status)
                .eq(headId != null, "head_id", headId)
                .groupBy("activity_id"));
        if (CollUtil.isEmpty(result)) {
            return Collections.emptyMap();
        }
        // Convert data
        return CollectionUtils.convertMap(result,
                record -> MapUtil.getLong(record, "activityId"),
                record -> MapUtil.getInt(record, "recordCount"));
    }

    default PageResult<CombinationRecordDO> selectPage(CombinationRecordReqPageVO pageVO) {
        LambdaQueryWrapperX<CombinationRecordDO> queryWrapper = new LambdaQueryWrapperX<CombinationRecordDO>()
                .eqIfPresent(CombinationRecordDO::getStatus, pageVO.getStatus())
                .betweenIfPresent(CombinationRecordDO::getCreateTime, pageVO.getCreateTime());
        // If headId Not empty，Instructions for querying the leader of a specified group + Team member's group record
        if (pageVO.getHeadId() != null) {
            queryWrapper.eq(CombinationRecordDO::getId, pageVO.getHeadId()) // Team Leader
                    .or().eq(CombinationRecordDO::getHeadId, pageVO.getHeadId()); // Team member
        }
        return selectPage(pageVO, queryWrapper);
    }

    /**
     * Query the number of records with specified conditions
     *
     * @param status       Status，Can be null
     * @param virtualGroup Whether to form a virtual group，Can be null
     * @param headId       Team Leader Number，Can be null
     * @return Number of records
     */
    default Long selectCountByHeadAndStatusAndVirtualGroup(Integer status, Boolean virtualGroup, Long headId) {
        return selectCount(new LambdaQueryWrapperX<CombinationRecordDO>()
                .eqIfPresent(CombinationRecordDO::getStatus, status)
                .eqIfPresent(CombinationRecordDO::getVirtualGroup, virtualGroup)
                .eqIfPresent(CombinationRecordDO::getHeadId, headId));
    }

    /**
     * Query user group purchase records（DISTINCT Deduplication），That is to say, when checking the member table to see how many users have participated in group buying activities, each person is only counted once
     *
     * @return Number of users who have participated in group buying
     */
    default Long selectUserCount() {
        return selectCount(new QueryWrapper<CombinationRecordDO>()
                .select("DISTINCT (user_id)"));
    }

    default List<CombinationRecordDO> selectListByHeadIdAndStatusAndExpireTimeLt(Long headId, Integer status, LocalDateTime dateTime) {
        return selectList(new LambdaQueryWrapperX<CombinationRecordDO>()
                .eq(CombinationRecordDO::getHeadId, headId)
                .eq(CombinationRecordDO::getStatus, status)
                .lt(CombinationRecordDO::getExpireTime, dateTime));
    }

    default List<CombinationRecordDO> selectListByHeadId(Long headId) {
        return selectList(CombinationRecordDO::getHeadId, headId);
    }

    default PageResult<CombinationRecordDO> selectPage(Long userId, AppCombinationRecordPageReqVO pageReqVO) {
        LambdaQueryWrapperX<CombinationRecordDO> queryWrapper = new LambdaQueryWrapperX<CombinationRecordDO>()
                .eq(CombinationRecordDO::getUserId, userId)
                .eqIfPresent(CombinationRecordDO::getStatus, pageReqVO.getStatus());
        return selectPage(pageReqVO, queryWrapper);
    }

}
