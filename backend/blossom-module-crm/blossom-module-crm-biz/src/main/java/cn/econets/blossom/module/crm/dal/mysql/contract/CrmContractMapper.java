package cn.econets.blossom.module.crm.dal.mysql.contract;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractPageReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.econets.blossom.module.crm.enums.common.CrmAuditStatusEnum;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.util.CrmQueryWrapperUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * CRM Contract Mapper
 *
 */
@Mapper
public interface CrmContractMapper extends BaseMapperX<CrmContractDO> {

    default int updateOwnerUserIdById(Long id, Long ownerUserId) {
        return update(new LambdaUpdateWrapper<CrmContractDO>()
                .eq(CrmContractDO::getId, id)
                .set(CrmContractDO::getOwnerUserId, ownerUserId));
    }

    default PageResult<CrmContractDO> selectPageByCustomerId(CrmContractPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<CrmContractDO>()
                .eq(CrmContractDO::getCustomerId, pageReqVO.getCustomerId())
                .likeIfPresent(CrmContractDO::getNo, pageReqVO.getNo())
                .likeIfPresent(CrmContractDO::getName, pageReqVO.getName())
                .eqIfPresent(CrmContractDO::getCustomerId, pageReqVO.getCustomerId())
                .eqIfPresent(CrmContractDO::getBusinessId, pageReqVO.getBusinessId())
                .orderByDesc(CrmContractDO::getId));
    }

    default PageResult<CrmContractDO> selectPage(CrmContractPageReqVO pageReqVO, Long userId) {
        MPJLambdaWrapperX<CrmContractDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_CONTRACT.getType(),
                CrmContractDO::getId, userId, pageReqVO.getSceneType(), Boolean.FALSE);
        // Splice your own query conditions
        query.selectAll(CrmContractDO.class)
                .likeIfPresent(CrmContractDO::getNo, pageReqVO.getNo())
                .likeIfPresent(CrmContractDO::getName, pageReqVO.getName())
                .eqIfPresent(CrmContractDO::getCustomerId, pageReqVO.getCustomerId())
                .eqIfPresent(CrmContractDO::getBusinessId, pageReqVO.getBusinessId())
                .eqIfPresent(CrmContractDO::getAuditStatus, pageReqVO.getAuditStatus())
                .orderByDesc(CrmContractDO::getId);

        // Backlog: Expiring contract
        LocalDateTime beginOfToday = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
        LocalDateTime endOfToday = LocalDateTimeUtil.endOfDay(LocalDateTime.now());
        if (CrmContractPageReqVO.EXPIRY_TYPE_ABOUT_TO_EXPIRE.equals(pageReqVO.getExpiryType())) { // Expiring Soon
            // TODO: Need to configure Days of advance reminder
            int REMIND_DAYS = 20;
            query.eq(CrmContractDO::getAuditStatus, CrmAuditStatusEnum.APPROVE.getStatus())
                    .between(CrmContractDO::getEndTime, beginOfToday, endOfToday.plusDays(REMIND_DAYS));
        } else if (CrmContractPageReqVO.EXPIRY_TYPE_EXPIRED.equals(pageReqVO.getExpiryType())) { // Expired
            query.eq(CrmContractDO::getAuditStatus, CrmAuditStatusEnum.APPROVE.getStatus())
                    .lt(CrmContractDO::getEndTime, endOfToday);
        }
        return selectJoinPage(pageReqVO, CrmContractDO.class, query);
    }

    default List<CrmContractDO> selectBatchIds(Collection<Long> ids, Long userId) {
        MPJLambdaWrapperX<CrmContractDO> query = new MPJLambdaWrapperX<>();
        // Constructing data permission table connection conditions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_CONTRACT.getType(), ids, userId);
        // Splice your own query conditions
        query.selectAll(CrmContractDO.class).in(CrmContractDO::getId, ids).orderByDesc(CrmContractDO::getId);
        return selectJoinList(CrmContractDO.class, query);
    }

    default Long selectCountByContactId(Long contactId) {
        return selectCount(CrmContractDO::getContactId, contactId);
    }

    default Long selectCountByBusinessId(Long businessId) {
        return selectCount(CrmContractDO::getBusinessId, businessId);
    }

}
