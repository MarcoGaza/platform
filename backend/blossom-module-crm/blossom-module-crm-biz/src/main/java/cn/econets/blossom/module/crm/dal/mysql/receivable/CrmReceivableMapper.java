package cn.econets.blossom.module.crm.dal.mysql.receivable;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable.CrmReceivablePageReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivableDO;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.util.CrmQueryWrapperUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Payback Mapper
 *
 */
@Mapper
public interface CrmReceivableMapper extends BaseMapperX<CrmReceivableDO> {

    default int updateOwnerUserIdById(Long id, Long ownerUserId) {
        return update(new LambdaUpdateWrapper<CrmReceivableDO>()
                .eq(CrmReceivableDO::getId, id)
                .set(CrmReceivableDO::getOwnerUserId, ownerUserId));
    }

    default PageResult<CrmReceivableDO> selectPageByCustomerId(CrmReceivablePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmReceivableDO>()
                .eq(CrmReceivableDO::getCustomerId, reqVO.getCustomerId()) // Must pass
                .eqIfPresent(CrmReceivableDO::getNo, reqVO.getNo())
                .eqIfPresent(CrmReceivableDO::getPlanId, reqVO.getPlanId())
                .orderByDesc(CrmReceivableDO::getId));
    }

    default PageResult<CrmReceivableDO> selectPage(CrmReceivablePageReqVO pageReqVO, Long userId) {
        MPJLambdaWrapperX<CrmReceivableDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE.getType(),
                CrmReceivableDO::getId, userId, pageReqVO.getSceneType(), Boolean.FALSE);
        // Splice your own query conditions
        query.selectAll(CrmReceivableDO.class)
                .eqIfPresent(CrmReceivableDO::getNo, pageReqVO.getNo())
                .eqIfPresent(CrmReceivableDO::getPlanId, pageReqVO.getPlanId())
                .eqIfPresent(CrmReceivableDO::getAuditStatus, pageReqVO.getAuditStatus())
                .orderByDesc(CrmReceivableDO::getId);
        return selectJoinPage(pageReqVO, CrmReceivableDO.class, query);
    }

    default List<CrmReceivableDO> selectBatchIds(Collection<Long> ids, Long userId) {
        MPJLambdaWrapperX<CrmReceivableDO> query = new MPJLambdaWrapperX<>();
        // Query conditions for splicing data permissions
        CrmQueryWrapperUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE.getType(), ids, userId);
        // Splice your own query conditions
        query.selectAll(CrmReceivableDO.class).in(CrmReceivableDO::getId, ids).orderByDesc(CrmReceivableDO::getId);
        return selectJoinList(CrmReceivableDO.class, query);
    }

}
