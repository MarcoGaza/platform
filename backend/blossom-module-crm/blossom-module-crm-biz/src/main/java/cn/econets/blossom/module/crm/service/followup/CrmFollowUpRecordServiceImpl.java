package cn.econets.blossom.module.crm.service.followup;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.followup.vo.CrmFollowUpRecordPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.followup.vo.CrmFollowUpRecordSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.followup.CrmFollowUpRecordDO;
import cn.econets.blossom.module.crm.dal.mysql.followup.CrmFollowUpRecordMapper;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.service.business.CrmBusinessService;
import cn.econets.blossom.module.crm.service.clue.CrmClueService;
import cn.econets.blossom.module.crm.service.contact.CrmContactService;
import cn.econets.blossom.module.crm.service.contract.CrmContractService;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import cn.econets.blossom.module.crm.service.followup.bo.CrmFollowUpCreateReqBO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;
import cn.econets.blossom.module.crm.service.permission.CrmPermissionService;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.FOLLOW_UP_RECORD_DELETE_DENIED;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.FOLLOW_UP_RECORD_NOT_EXISTS;

/**
 * Follow-up records Service Implementation class
 *
 */
@Service
@Validated
public class CrmFollowUpRecordServiceImpl implements CrmFollowUpRecordService {

    @Resource
    private CrmFollowUpRecordMapper crmFollowUpRecordMapper;

    @Resource
    @Lazy
    private CrmPermissionService permissionService;
    @Resource
    @Lazy
    private CrmBusinessService businessService;
    @Resource
    @Lazy
    private CrmClueService clueService;
    @Resource
    @Lazy
    private CrmContactService contactService;
    @Resource
    @Lazy
    private CrmContractService contractService;
    @Resource
    @Lazy
    private CrmCustomerService customerService;

    @Override
    @CrmPermission(bizTypeValue = "#createReqVO.bizType", bizId = "#createReqVO.bizId", level = CrmPermissionLevelEnum.WRITE)
    public Long createFollowUpRecord(CrmFollowUpRecordSaveReqVO createReqVO) {
        // Create update record
        CrmFollowUpRecordDO followUpRecord = BeanUtils.toBean(createReqVO, CrmFollowUpRecordDO.class);
        crmFollowUpRecordMapper.insert(followUpRecord);

        LocalDateTime now = LocalDateTime.now();
        CrmUpdateFollowUpReqBO updateFollowUpReqBO = new CrmUpdateFollowUpReqBO().setBizId(followUpRecord.getBizId())
                .setContactLastTime(now).setContactNextTime(followUpRecord.getNextTime()).setContactLastContent(followUpRecord.getContent());
        // 2. Update bizId Corresponding records；
        if (ObjUtil.notEqual(CrmBizTypeEnum.CRM_BUSINESS.getType(), followUpRecord.getBizType())) { // Update business opportunity follow-up information
            businessService.updateBusinessFollowUpBatch(Collections.singletonList(updateFollowUpReqBO));
        }
        if (ObjUtil.notEqual(CrmBizTypeEnum.CRM_LEADS.getType(), followUpRecord.getBizType())) { // Update lead follow-up information
            clueService.updateClueFollowUp(updateFollowUpReqBO);
        }
        if (ObjUtil.notEqual(CrmBizTypeEnum.CRM_CONTACT.getType(), followUpRecord.getBizType())) { // Update contact follow-up information
            contactService.updateContactFollowUpBatch(Collections.singletonList(updateFollowUpReqBO));
        }
        if (ObjUtil.notEqual(CrmBizTypeEnum.CRM_CONTRACT.getType(), followUpRecord.getBizType())) { // Update contract follow-up information
            contractService.updateContractFollowUp(updateFollowUpReqBO);
        }
        if (ObjUtil.notEqual(CrmBizTypeEnum.CRM_CUSTOMER.getType(), followUpRecord.getBizType())) { // Update customer follow-up information
            customerService.updateCustomerFollowUp(updateFollowUpReqBO);
        }

        // 3.1 Update contactIds Corresponding records，Do not update lastTime Japanese lastContent
        if (CollUtil.isNotEmpty(createReqVO.getContactIds())) {
            contactService.updateContactFollowUpBatch(convertList(createReqVO.getContactIds(),
                    contactId -> updateFollowUpReqBO.setBizId(contactId).setContactLastTime(null).setContactLastContent(null)));
        }
        // 3.2 Update required businessIds Corresponding records，Do not update lastTime Japanese lastContent
        if (CollUtil.isNotEmpty(createReqVO.getBusinessIds())) {
            businessService.updateBusinessFollowUpBatch(convertList(createReqVO.getBusinessIds(),
                    businessId -> updateFollowUpReqBO.setBizId(businessId).setContactLastTime(null).setContactLastContent(null)));
        }
        return followUpRecord.getId();
    }

    @Override
    public void createFollowUpRecordBatch(List<CrmFollowUpCreateReqBO> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        crmFollowUpRecordMapper.insertBatch(BeanUtils.toBean(list, CrmFollowUpRecordDO.class));
    }

    @Override
    public void deleteFollowUpRecord(Long id, Long userId) {
        // Check existence
        CrmFollowUpRecordDO followUpRecord = validateFollowUpRecordExists(id);
        // Verify permissions
        if (!permissionService.hasPermission(followUpRecord.getBizType(), followUpRecord.getBizId(), userId, CrmPermissionLevelEnum.OWNER)) {
            throw exception(FOLLOW_UP_RECORD_DELETE_DENIED);
        }

        // Delete
        crmFollowUpRecordMapper.deleteById(id);
    }

    @Override
    public void deleteFollowUpRecordByBiz(Integer bizType, Long bizId) {
        crmFollowUpRecordMapper.deleteByBiz(bizType, bizId);
    }

    private CrmFollowUpRecordDO validateFollowUpRecordExists(Long id) {
        CrmFollowUpRecordDO followUpRecord = crmFollowUpRecordMapper.selectById(id);
        if (followUpRecord == null) {
            throw exception(FOLLOW_UP_RECORD_NOT_EXISTS);
        }
        return followUpRecord;
    }

    @Override
    public CrmFollowUpRecordDO getFollowUpRecord(Long id) {
        return crmFollowUpRecordMapper.selectById(id);
    }

    @Override
    @CrmPermission(bizTypeValue = "#pageReqVO.bizType", bizId = "#pageReqVO.bizId", level = CrmPermissionLevelEnum.READ)
    public PageResult<CrmFollowUpRecordDO> getFollowUpRecordPage(CrmFollowUpRecordPageReqVO pageReqVO) {
        return crmFollowUpRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CrmFollowUpRecordDO> getFollowUpRecordByBiz(Integer bizType, Collection<Long> bizIds) {
        return crmFollowUpRecordMapper.selectListByBiz(bizType, bizIds);
    }

}
