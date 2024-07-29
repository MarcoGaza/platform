package cn.econets.blossom.module.promotion.dal.mysql.discount;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountActivityDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Limited time discount event Mapper
 *
 */
@Mapper
public interface DiscountActivityMapper extends BaseMapperX<DiscountActivityDO> {

    default PageResult<DiscountActivityDO> selectPage(DiscountActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiscountActivityDO>()
                .likeIfPresent(DiscountActivityDO::getName, reqVO.getName())
                .eqIfPresent(DiscountActivityDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DiscountActivityDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DiscountActivityDO::getId));
    }

    /**
     * Get the activity list of the specified activity number and
     * The start time and end time are less than the given time dateTime Activity list
     *
     * @param ids      Activity number
     * @param dateTime Specify date
     * @return Activity List
     */
    default List<DiscountActivityDO> selectListByIdsAndDateTimeLt(Collection<Long> ids, LocalDateTime dateTime) {
        return selectList(new LambdaQueryWrapperX<DiscountActivityDO>()
                .in(DiscountActivityDO::getId, ids)
                .lt(DiscountActivityDO::getStartTime, dateTime)
                .gt(DiscountActivityDO::getEndTime, dateTime)// Start time < Specify time < End time，That is, get the activities in the specified time period
                .orderByDesc(DiscountActivityDO::getCreateTime));
    }

}
