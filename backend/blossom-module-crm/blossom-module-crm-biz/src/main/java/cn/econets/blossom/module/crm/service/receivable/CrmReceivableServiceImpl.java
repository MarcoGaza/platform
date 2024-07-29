package cn.econets.blossom.module.crm.service.receivable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable.CrmReceivableCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable.CrmReceivablePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable.CrmReceivableUpdateReqVO;
import cn.econets.blossom.module.crm.convert.receivable.CrmReceivableConvert;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivableDO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivablePlanDO;
import cn.econets.blossom.module.crm.dal.mysql.receivable.CrmReceivableMapper;
import cn.econets.blossom.module.crm.enums.common.CrmAuditStatusEnum;
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
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;

/**
 * CRM Payback Service Implementation class
 *
 */
@Service
@Validated
public class CrmReceivableServiceImpl implements CrmReceivableService {

    @Resource
    private CrmReceivableMapper receivableMapper;

    @Resource
    private CrmContractService contractService;
    @Resource
    private CrmCustomerService customerService;
    @Resource
    private CrmReceivablePlanService receivablePlanService;
    @Resource
    private CrmPermissionService permissionService;

    @Override
    @LogRecord(type = CRM_RECEIVABLE_TYPE, subType = CRM_RECEIVABLE_CREATE_SUB_TYPE, bizNo = "{{#receivable.id}}",
            success = CRM_RECEIVABLE_CREATE_SUCCESS)
    public Long createReceivable(CrmReceivableCreateReqVO createReqVO, Long userId) {
        // Insert repayment
        CrmReceivableDO receivable = CrmReceivableConvert.INSTANCE.convert(createReqVO);
        if (ObjectUtil.isNull(receivable.getAuditStatus())) {
            receivable.setAuditStatus(CommonStatusEnum.ENABLE.getStatus());
        }
        receivable.setAuditStatus(CrmAuditStatusEnum.DRAFT.getStatus());

        // TODO @liuhongfeng：Generally speaking，Logical writing，You need to check first，Post-operation db；So，You check Should be placed  CrmReceivableDO receivable Before；
        checkReceivable(receivable);

        receivableMapper.insert(receivable);
        // 3. Create data permissions
        permissionService.createPermission(new CrmPermissionCreateReqBO().setBizType(CrmBizTypeEnum.CRM_RECEIVABLE.getType())
                .setBizId(receivable.getId()).setUserId(userId).setLevel(CrmPermissionLevelEnum.OWNER.getLevel())); // Set the person who is currently operating as the responsible person
        // TODO @liuhongfeng：Need to update the association plan

        // 4. Record operation log context
        LogRecordContext.putVariable("receivable", receivable);
        return receivable.getId();
    }

    // TODO @liuhongfeng：Pay attention to the layout of the brackets here；
    private void checkReceivable(CrmReceivableDO receivable) {
        // TODO @liuhongfeng：Verification no Uniqueness
        // TODO @liuhongfeng：This is suitable for parameter verification
        if (ObjectUtil.isNull(receivable.getContractId())) {
            throw exception(CONTRACT_NOT_EXISTS);
        }

        CrmContractDO contract = contractService.getContract(receivable.getContractId());
        if (ObjectUtil.isNull(contract)) {
            throw exception(CONTRACT_NOT_EXISTS);
        }

        CrmCustomerDO customer = customerService.getCustomer(receivable.getCustomerId());
        if (ObjectUtil.isNull(customer)) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }

        CrmReceivablePlanDO receivablePlan = receivablePlanService.getReceivablePlan(receivable.getPlanId());
        if (ObjectUtil.isNull(receivablePlan)) {
            throw exception(RECEIVABLE_PLAN_NOT_EXISTS);
        }

    }

    @Override
    @LogRecord(type = CRM_RECEIVABLE_TYPE, subType = CRM_RECEIVABLE_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = CRM_RECEIVABLE_UPDATE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE, bizId = "#updateReqVO.id", level = CrmPermissionLevelEnum.WRITE)
    public void updateReceivable(CrmReceivableUpdateReqVO updateReqVO) {
        // Check existence
        CrmReceivableDO oldReceivable = validateReceivableExists(updateReqVO.getId());
        // TODO @liuhongfeng：Only in draft、Under review，Can submit changes

        // Update repayment
        CrmReceivableDO updateObj = CrmReceivableConvert.INSTANCE.convert(updateReqVO);
        receivableMapper.updateById(updateObj);

        // TODO @liuhongfeng：Need to update the association plan
        // 3. Record operation log context
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldReceivable, CrmReceivableUpdateReqVO.class));
        LogRecordContext.putVariable("receivable", oldReceivable);
    }

    // TODO @liuhongfeng：Missing an interface to cancel a contract；Only draft、Can be cancelled during approval；CrmAuditStatusEnum

    // TODO @liuhongfeng：Missing an interface to initiate approval；Only drafts can be approved；CrmAuditStatusEnum

    @Override
    @LogRecord(type = CRM_RECEIVABLE_TYPE, subType = CRM_RECEIVABLE_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_RECEIVABLE_DELETE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void deleteReceivable(Long id) {
        // TODO @liuhongfeng：If CrmReceivablePlanDO Used，Deletion is not allowed
        // Check existence
        CrmReceivableDO receivable = validateReceivableExists(id);
        // Delete
        receivableMapper.deleteById(id);

        // Delete data permission
        permissionService.deletePermission(CrmBizTypeEnum.CRM_CUSTOMER.getType(), id);

        // Record operation log context
        LogRecordContext.putVariable("receivable", receivable);
    }

    private CrmReceivableDO validateReceivableExists(Long id) {
        CrmReceivableDO receivable = receivableMapper.selectById(id);
        if (receivable == null) {
            throw exception(RECEIVABLE_NOT_EXISTS);
        }
        return receivable;
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE, bizId = "#id", level = CrmPermissionLevelEnum.READ)
    public CrmReceivableDO getReceivable(Long id) {
        return receivableMapper.selectById(id);
    }

    @Override
    public List<CrmReceivableDO> getReceivableList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return receivableMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CrmReceivableDO> getReceivablePage(CrmReceivablePageReqVO pageReqVO, Long userId) {
        return receivableMapper.selectPage(pageReqVO, userId);
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#pageReqVO.customerId", level = CrmPermissionLevelEnum.READ)
    public PageResult<CrmReceivableDO> getReceivablePageByCustomerId(CrmReceivablePageReqVO pageReqVO) {
        return receivableMapper.selectPageByCustomerId(pageReqVO);
    }

}
