package cn.econets.blossom.module.crm.service.receivable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanUpdateReqVO;
import cn.econets.blossom.module.crm.convert.receivable.CrmReceivablePlanConvert;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivablePlanDO;
import cn.econets.blossom.module.crm.dal.mysql.receivable.CrmReceivablePlanMapper;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.service.contract.CrmContractService;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import cn.econets.blossom.module.crm.service.permission.CrmPermissionService;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionCreateReqBO;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;

// TODO @liuhongfeng：Reference CrmReceivableServiceImpl Written by todo Ha；

/**
 * Payment Refund Plan Service Implementation class
 *
 */
@Service
@Validated
public class CrmReceivablePlanServiceImpl implements CrmReceivablePlanService {

    @Resource
    private CrmReceivablePlanMapper receivablePlanMapper;

    @Resource
    private CrmContractService contractService;
    @Resource
    private CrmCustomerService customerService;
    @Resource
    private CrmPermissionService permissionService;

    @Override
    @LogRecord(type = CRM_RECEIVABLE_PLAN_TYPE, subType = CRM_RECEIVABLE_PLAN_CREATE_SUB_TYPE, bizNo = "{{#receivablePlan.id}}",
            success = CRM_RECEIVABLE_PLAN_CREATE_SUCCESS)
    public Long createReceivablePlan(CrmReceivablePlanCreateReqVO createReqVO, Long userId) {
        // TODO @liuhongfeng：The calculation of the first period；Based on contractId + contractDO Which repayment is the first one
        // TODO @liuhongfeng contractId：Check if the contract exists
        // Insert
        CrmReceivablePlanDO receivablePlan = CrmReceivablePlanConvert.INSTANCE.convert(createReqVO);
        receivablePlan.setFinishStatus(false);

        checkReceivablePlan(receivablePlan);

        receivablePlanMapper.insert(receivablePlan);
        // Create data permissions
        permissionService.createPermission(new CrmPermissionCreateReqBO().setUserId(userId)
                .setBizType(CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType()).setBizId(receivablePlan.getId())
                .setLevel(CrmPermissionLevelEnum.OWNER.getLevel()));

        // 4. Record operation log context
        LogRecordContext.putVariable("receivablePlan", receivablePlan);
        return receivablePlan.getId();
    }

    private void checkReceivablePlan(CrmReceivablePlanDO receivablePlan) {

        if (ObjectUtil.isNull(receivablePlan.getContractId())) {
            throw exception(CONTRACT_NOT_EXISTS);
        }

        CrmContractDO contract = contractService.getContract(receivablePlan.getContractId());
        if (ObjectUtil.isNull(contract)) {
            throw exception(CONTRACT_NOT_EXISTS);
        }

        CrmCustomerDO customer = customerService.getCustomer(receivablePlan.getCustomerId());
        if (ObjectUtil.isNull(customer)) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }

    }

    @Override
    @LogRecord(type = CRM_RECEIVABLE_PLAN_TYPE, subType = CRM_RECEIVABLE_PLAN_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = CRM_RECEIVABLE_PLAN_UPDATE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE_PLAN, bizId = "#updateReqVO.id", level = CrmPermissionLevelEnum.WRITE)
    public void updateReceivablePlan(CrmReceivablePlanUpdateReqVO updateReqVO) {
        // TODO @liuhongfeng：If there is already a corresponding repayment，Editing is not allowed；
        // Check existence
        CrmReceivablePlanDO oldReceivablePlan = validateReceivablePlanExists(updateReqVO.getId());

        // Update
        CrmReceivablePlanDO updateObj = CrmReceivablePlanConvert.INSTANCE.convert(updateReqVO);
        receivablePlanMapper.updateById(updateObj);

        // 3. Record operation log context
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldReceivablePlan, CrmReceivablePlanUpdateReqVO.class));
        LogRecordContext.putVariable("receivablePlan", oldReceivablePlan);
    }

    @Override
    @LogRecord(type = CRM_RECEIVABLE_PLAN_TYPE, subType = CRM_RECEIVABLE_PLAN_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_RECEIVABLE_PLAN_DELETE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE_PLAN, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void deleteReceivablePlan(Long id) {
        // Check existence
        CrmReceivablePlanDO receivablePlan = validateReceivablePlanExists(id);
        // Delete
        receivablePlanMapper.deleteById(id);
        // Delete data permissions
        permissionService.deletePermission(CrmBizTypeEnum.CRM_CUSTOMER.getType(), id);
        // Record operation log context
        LogRecordContext.putVariable("receivablePlan", receivablePlan);
    }

    private CrmReceivablePlanDO validateReceivablePlanExists(Long id) {
        CrmReceivablePlanDO receivablePlan = receivablePlanMapper.selectById(id);
        if (receivablePlan == null) {
            throw exception(RECEIVABLE_PLAN_NOT_EXISTS);
        }
        return receivablePlan;
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE_PLAN, bizId = "#id", level = CrmPermissionLevelEnum.READ)
    public CrmReceivablePlanDO getReceivablePlan(Long id) {
        return receivablePlanMapper.selectById(id);
    }

    @Override
    public List<CrmReceivablePlanDO> getReceivablePlanList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return receivablePlanMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CrmReceivablePlanDO> getReceivablePlanPage(CrmReceivablePlanPageReqVO pageReqVO, Long userId) {
        return receivablePlanMapper.selectPage(pageReqVO, userId);
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#pageReqVO.customerId", level = CrmPermissionLevelEnum.READ)
    public PageResult<CrmReceivablePlanDO> getReceivablePlanPageByCustomerId(CrmReceivablePlanPageReqVO pageReqVO) {
        return receivablePlanMapper.selectPageByCustomerId(pageReqVO);
    }

}
