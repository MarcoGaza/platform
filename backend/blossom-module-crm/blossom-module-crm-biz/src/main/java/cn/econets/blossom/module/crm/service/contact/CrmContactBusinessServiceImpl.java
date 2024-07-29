package cn.econets.blossom.module.crm.service.contact;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.module.crm.controller.admin.contact.vo.CrmContactBusinessReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.dal.mysql.contactbusinesslink.CrmContactBusinessMapper;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.service.business.CrmBusinessService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.BUSINESS_NOT_EXISTS;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.CONTACT_NOT_EXISTS;

/**
 * The relationship between contacts and opportunities Service Implementation class
 *
 */
@Service
@Validated
public class CrmContactBusinessServiceImpl implements CrmContactBusinessService {

    @Resource
    private CrmContactBusinessMapper contactBusinessMapper;

    @Resource
    @Lazy // Delayed loading，To solve the problem of delayed loading
    private CrmBusinessService businessService;
    @Resource
    @Lazy // Delayed loading，To solve the problem of delayed loading
    private CrmContactService contactService;

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CONTACT, bizId = "#createReqVO.contactId", level = CrmPermissionLevelEnum.WRITE)
    public void createContactBusinessList(CrmContactBusinessReqVO createReqVO) {
        CrmContactDO contact = contactService.getContact(createReqVO.getContactId());
        if (contact == null) {
            throw exception(CONTACT_NOT_EXISTS);
        }
        // Traversal processing，Considering that the number is generally not too large，Simple code processing
        List<CrmContactBusinessDO> saveDOList = new ArrayList<>();
        createReqVO.getBusinessIds().forEach(businessId -> {
            CrmBusinessDO business = businessService.getBusiness(businessId);
            if (business == null) {
                throw exception(BUSINESS_NOT_EXISTS);
            }
            // Association weight determination
            if (contactBusinessMapper.selectByContactIdAndBusinessId(createReqVO.getContactId(), businessId) != null) {
                return;
            }
            saveDOList.add(new CrmContactBusinessDO(null, createReqVO.getContactId(), businessId));
        });
        // Batch insert
        if (CollUtil.isNotEmpty(saveDOList)) {
            contactBusinessMapper.insertBatch(saveDOList);
        }
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CONTACT, bizId = "#deleteReqVO.contactId", level = CrmPermissionLevelEnum.WRITE)
    public void deleteContactBusinessList(CrmContactBusinessReqVO deleteReqVO) {
        CrmContactDO contact = contactService.getContact(deleteReqVO.getContactId());
        if (contact == null) {
            throw exception(CONTACT_NOT_EXISTS);
        }
        // Delete directly
        contactBusinessMapper.deleteByContactIdAndBusinessId(
                deleteReqVO.getContactId(), deleteReqVO.getBusinessIds());
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CONTACT, bizId = "#contactId", level = CrmPermissionLevelEnum.WRITE)
    public void deleteContactBusinessByContactId(Long contactId) {
        contactBusinessMapper.delete(CrmContactBusinessDO::getContactId, contactId);
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CONTACT, bizId = "#contactId", level = CrmPermissionLevelEnum.READ)
    public List<CrmContactBusinessDO> getContactBusinessListByContactId(Long contactId) {
        return contactBusinessMapper.selectListByContactId(contactId);
    }

    @Override
    public void insert(CrmContactBusinessDO contactBusiness) {
        contactBusinessMapper.insert(contactBusiness);
    }

}
