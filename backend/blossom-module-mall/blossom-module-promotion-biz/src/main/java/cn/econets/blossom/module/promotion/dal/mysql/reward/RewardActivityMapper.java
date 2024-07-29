package cn.econets.blossom.module.promotion.dal.mysql.reward;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.reward.RewardActivityDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Save a lot of money and get a free gift Mapper
 *
 */
@Mapper
public interface RewardActivityMapper extends BaseMapperX<RewardActivityDO> {

    default PageResult<RewardActivityDO> selectPage(RewardActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RewardActivityDO>()
                .likeIfPresent(RewardActivityDO::getName, reqVO.getName())
                .eqIfPresent(RewardActivityDO::getStatus, reqVO.getStatus())
                .orderByDesc(RewardActivityDO::getId));
    }

    default List<RewardActivityDO> selectListByStatus(Collection<Integer> statuses) {
        return selectList(RewardActivityDO::getStatus, statuses);
    }

    default List<RewardActivityDO> selectListByProductScopeAndStatus(Integer productScope, Integer status) {
        return selectList(new LambdaQueryWrapperX<RewardActivityDO>()
                .eq(RewardActivityDO::getProductScope, productScope)
                .eq(RewardActivityDO::getStatus, status));
    }

    default List<RewardActivityDO> selectListBySpuIdsAndStatus(Collection<Long> spuIds, Integer status) {
        Function<Collection<Long>, String> productScopeValuesFindInSetFunc = ids -> ids.stream()
                .map(id -> StrUtil.format("FIND_IN_SET({}, product_spu_ids) ", id))
                .collect(Collectors.joining(" OR "));
        return selectList(new QueryWrapper<RewardActivityDO>()
                .eq("status", status)
                .apply(productScopeValuesFindInSetFunc.apply(spuIds)));
    }

    /**
     * Get the activity list of the specified activity number and
     * The start time and end time are less than the given time dateTime Activity list
     *
     * @param ids      Activity number
     * @param dateTime Specify date
     * @return Activity List
     */
    default List<RewardActivityDO> selectListByIdsAndDateTimeLt(Collection<Long> ids, LocalDateTime dateTime) {
        return selectList(new LambdaQueryWrapperX<RewardActivityDO>()
                .in(RewardActivityDO::getId, ids)
                .lt(RewardActivityDO::getStartTime, dateTime)
                .gt(RewardActivityDO::getEndTime, dateTime)// Start time < Specify time < End time，That is, get the activities in the specified time period
                .orderByDesc(RewardActivityDO::getCreateTime)
        );
    }

}
