package cn.econets.blossom.module.crm.dal.mysql.customer;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.econets.blossom.module.crm.controller.admin.backlog.vo.CrmTodayCustomerPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.CrmCustomerPageReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.util.CrmQueryWrapperUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Customer Mapper
 *
 */
@Mapper
public interface CrmCustomerMapper extends BaseMapperX<CrmCustomerDO> {

    default Long selectCountByLockStatusAndOwnerUserId(Boolean lockStatus, Long ownerUserId) {
        return selectCount(new LambdaUpdateWrapper<CrmCustomerDO>()
                .eq(CrmCustomerDO::getLockStatus, lockStatus)
                .eq(CrmCustomerDO::getOwnerUserId, ownerUserId));
    }

    default Long selectCountByDealStatusAndOwnerUserId(@Nullable Boolean dealStatus, Long ownerUserId) {
        return selectCount(new LambdaQueryWrapperX<CrmCustomerDO>()
                .eqIfPresent(CrmCustomerDO::getDealStatus, dealStatus)
                .eq(CrmCustomerDO::getOwnerUserId, ownerUserId));
    }

    default int updateOwnerUserIdById(Long id, Long ownerUserId) {
        return update(new LambdaUpdateWrapper<CrmCustomerDO>()
                .eq(CrmCustomerDO::getId, id)
                .set(CrmCustomerDO::getOwnerUserId, ownerUserId));
    }

    default PageResult<CrmCustomerDO> selectPage(CrmCustomerPageReqVO pageReqVO, Long userId) {
        MPJLambdaWrapperX<CrmCustomerDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_CUSTOMER.getType(),
                CrmCustomerDO::getId, userId, pageReqVO.getSceneType(), pageReqVO.getPool());
        // Splice your own query conditions
        query.selectAll(CrmCustomerDO.class)
                .likeIfPresent(CrmCustomerDO::getName, pageReqVO.getName())
                .eqIfPresent(CrmCustomerDO::getMobile, pageReqVO.getMobile())
                .eqIfPresent(CrmCustomerDO::getIndustryId, pageReqVO.getIndustryId())
                .eqIfPresent(CrmCustomerDO::getLevel, pageReqVO.getLevel())
                .eqIfPresent(CrmCustomerDO::getSource, pageReqVO.getSource());
        return selectJoinPage(pageReqVO, CrmCustomerDO.class, query);
    }

    default List<CrmCustomerDO> selectBatchIds(Collection<Long> ids, Long userId) {
        MPJLambdaWrapperX<CrmCustomerDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_CUSTOMER.getType(), ids, userId);
        // Splice your own query conditions
        query.selectAll(CrmCustomerDO.class).in(CrmCustomerDO::getId, ids).orderByDesc(CrmCustomerDO::getId);
        return selectJoinList(CrmCustomerDO.class, query);
    }

    /**
     * To do list - Need to contact the customer today
     *
     * @param pageReqVO Paging request parameters
     * @param userId    Current userID
     * @return Paged results
     */
    default PageResult<CrmCustomerDO> selectTodayCustomerPage(CrmTodayCustomerPageReqVO pageReqVO, Long userId) {
        MPJLambdaWrapperX<CrmCustomerDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_CUSTOMER.getType(),
                CrmCustomerDO::getId, userId, pageReqVO.getSceneType(), null);

        // Splice your own query conditions
        query.selectAll(CrmCustomerDO.class);
        if (pageReqVO.getContactStatus() != null) {
            LocalDateTime beginOfToday = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
            LocalDateTime endOfToday = LocalDateTimeUtil.endOfDay(LocalDateTime.now());
            if (pageReqVO.getContactStatus().equals(CrmTodayCustomerPageReqVO.CONTACT_TODAY)) { // Need to contact today
                query.between(CrmCustomerDO::getContactNextTime, beginOfToday, endOfToday);
            } else if (pageReqVO.getContactStatus().equals(CrmTodayCustomerPageReqVO.CONTACT_EXPIRED)) { // Overdue
                query.lt(CrmCustomerDO::getContactNextTime, beginOfToday);
            } else if (pageReqVO.getContactStatus().equals(CrmTodayCustomerPageReqVO.CONTACT_ALREADY)) { // Contacted
                query.between(CrmCustomerDO::getContactLastTime, beginOfToday, endOfToday);
            } else {
                throw new IllegalArgumentException("Unknown contact status：" + pageReqVO.getContactStatus());
            }
        }
        return selectJoinPage(pageReqVO, CrmCustomerDO.class, query);
    }

    default List<CrmCustomerDO> selectListByLockAndNotPool(Boolean lockStatus) {
        return selectList(new LambdaQueryWrapper<CrmCustomerDO>()
                .eq(CrmCustomerDO::getLockStatus, lockStatus)
                .gt(CrmCustomerDO::getOwnerUserId, 0));
    }

    default CrmCustomerDO selectByCustomerName(String name) {
        return selectOne(CrmCustomerDO::getName, name);
    }

    default PageResult<CrmCustomerDO> selectPutInPoolRemindCustomerPage(CrmCustomerPageReqVO pageReqVO,
                                                                        CrmCustomerPoolConfigDO poolConfigDO,
                                                                        Long userId) {
        MPJLambdaWrapperX<CrmCustomerDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_CUSTOMER.getType(),
                CrmCustomerDO::getId, userId, pageReqVO.getSceneType(), null);
        // TODO @dhb52：lock Situation，No need to remind me；

        // Splicing its own query conditions
        query.selectAll(CrmCustomerDO.class);
        // Situation 1：Unsettled Reminder Date Range
        Integer dealExpireDays = poolConfigDO.getDealExpireDays();
        LocalDateTime startDealRemindDate = LocalDateTimeUtil.beginOfDay(LocalDateTime.now())
                .minusDays(dealExpireDays);
        LocalDateTime endDealRemindDate = LocalDateTimeUtil.endOfDay(LocalDateTime.now())
                .minusDays(Math.max(dealExpireDays - poolConfigDO.getNotifyDays(), 0));
        // Situation 2：No follow-up reminder date range
        Integer contactExpireDays = poolConfigDO.getContactExpireDays();
        LocalDateTime startContactRemindDate = LocalDateTimeUtil.beginOfDay(LocalDateTime.now())
                .minusDays(contactExpireDays);
        LocalDateTime endContactRemindDate = LocalDateTimeUtil.endOfDay(LocalDateTime.now())
                .minusDays(Math.max(contactExpireDays - poolConfigDO.getNotifyDays(), 0));
        query
                // Situation 1：1. Unsold items are put into the high seas reminder
                .eq(CrmCustomerDO::getDealStatus, false)
                .between(CrmCustomerDO::getCreateTime, startDealRemindDate, endDealRemindDate)
                // Case 2：No follow-up reminder on the high seas
                .or() // 2.1 contactLastTime Empty TODO Should we set a default value for this?；
                .isNull(CrmCustomerDO::getContactLastTime)
                .between(CrmCustomerDO::getCreateTime, startContactRemindDate, endContactRemindDate)
                .or() // 2.2 ContactLastTime Not empty
                .between(CrmCustomerDO::getContactLastTime, startContactRemindDate, endContactRemindDate);
        return selectJoinPage(pageReqVO, CrmCustomerDO.class, query);
    }

}
