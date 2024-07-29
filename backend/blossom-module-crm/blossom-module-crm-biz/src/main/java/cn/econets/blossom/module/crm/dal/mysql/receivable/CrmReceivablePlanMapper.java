package cn.econets.blossom.module.crm.dal.mysql.receivable;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanPageReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivablePlanDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.util.CrmQueryWrapperUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Payment Refund Plan Mapper
 *
 */
@Mapper
public interface CrmReceivablePlanMapper extends BaseMapperX<CrmReceivablePlanDO> {

    default int updateOwnerUserIdById(Long id, Long ownerUserId) {
        return update(new LambdaUpdateWrapper<CrmReceivablePlanDO>()
                .eq(CrmReceivablePlanDO::getId, id)
                .set(CrmReceivablePlanDO::getOwnerUserId, ownerUserId));
    }

    default PageResult<CrmReceivablePlanDO> selectPageByCustomerId(CrmReceivablePlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmReceivablePlanDO>()
                .eq(CrmReceivablePlanDO::getCustomerId, reqVO.getCustomerId()) // Must pass
                .eqIfPresent(CrmReceivablePlanDO::getContractId, reqVO.getContractId())
                .orderByDesc(CrmReceivablePlanDO::getId));
    }

    default PageResult<CrmReceivablePlanDO> selectPage(CrmReceivablePlanPageReqVO pageReqVO, Long userId) {
        MPJLambdaWrapperX<CrmReceivablePlanDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType(),
                CrmReceivablePlanDO::getId, userId, pageReqVO.getSceneType(), Boolean.FALSE);
        // Splice your own query conditions
        query.selectAll(CrmReceivablePlanDO.class)
                .eqIfPresent(CrmReceivablePlanDO::getCustomerId, pageReqVO.getCustomerId())
                .eqIfPresent(CrmReceivablePlanDO::getContractId, pageReqVO.getContractId())
                .orderByDesc(CrmReceivablePlanDO::getId);

        // Backlog: Payment Reminder Type
        LocalDateTime beginOfToday = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
        LocalDateTime endOfToday = LocalDateTimeUtil.endOfDay(LocalDateTime.now());
        if (CrmReceivablePlanPageReqVO.REMIND_TYPE_NEEDED.equals(pageReqVO.getRemindType())) { // Waiting for payment
            query.isNull(CrmReceivablePlanDO::getReceivableId)
                    .gt(CrmReceivablePlanDO::getReturnTime, beginOfToday)
                    // TODO @dhb52：Here is how to change it not to use to_days
                    .apply("to_days(return_time) <= to_days(now())+ remind_days");
        } else if (CrmReceivablePlanPageReqVO.REMIND_TYPE_EXPIRED.equals(pageReqVO.getRemindType())) {  // Overdue
            query.isNull(CrmReceivablePlanDO::getReceivableId)
                    .lt(CrmReceivablePlanDO::getReturnTime, endOfToday);
        } else if (CrmReceivablePlanPageReqVO.REMIND_TYPE_RECEIVED.equals(pageReqVO.getRemindType())) { // Payment received
            query.isNotNull(CrmReceivablePlanDO::getReceivableId)
                    .gt(CrmReceivablePlanDO::getReturnTime, beginOfToday)
                    // TODO @dhb52：Here is how to change it not to use to_days
                    .apply("to_days(return_time) <= to_days(now()) + remind_days");
        }

        return selectJoinPage(pageReqVO, CrmReceivablePlanDO.class, query);
    }

    default List<CrmReceivablePlanDO> selectBatchIds(Collection<Long> ids, Long userId) {
        MPJLambdaWrapperX<CrmReceivablePlanDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType(), ids, userId);
        // Splice your own query conditions
        query.selectAll(CrmReceivablePlanDO.class).in(CrmReceivablePlanDO::getId, ids).orderByDesc(CrmReceivablePlanDO::getId);
        return selectJoinList(CrmReceivablePlanDO.class, query);
    }

}
