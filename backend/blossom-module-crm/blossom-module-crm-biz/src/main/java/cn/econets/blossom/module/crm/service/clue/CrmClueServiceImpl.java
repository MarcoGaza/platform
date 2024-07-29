package cn.econets.blossom.module.crm.service.clue;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmCluePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmClueSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmClueTransferReqVO;
import cn.econets.blossom.module.crm.controller.admin.clue.vo.CrmClueTranslateReqVO;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.CrmCustomerSaveReqVO;
import cn.econets.blossom.module.crm.convert.clue.CrmClueConvert;
import cn.econets.blossom.module.crm.dal.dataobject.clue.CrmClueDO;
import cn.econets.blossom.module.crm.dal.dataobject.followup.CrmFollowUpRecordDO;
import cn.econets.blossom.module.crm.dal.mysql.clue.CrmClueMapper;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import cn.econets.blossom.module.crm.service.customer.bo.CrmCustomerCreateReqBO;
import cn.econets.blossom.module.crm.service.followup.CrmFollowUpRecordService;
import cn.econets.blossom.module.crm.service.followup.bo.CrmFollowUpCreateReqBO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;
import cn.econets.blossom.module.crm.service.permission.CrmPermissionService;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionCreateReqBO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static java.util.Collections.singletonList;

/**
 * Clues Service Implementation class
 *
 */
@Service
@Validated
public class CrmClueServiceImpl implements CrmClueService {

    @Resource
    private CrmClueMapper clueMapper;

    @Resource
    private CrmCustomerService customerService;

    @Resource
    private CrmPermissionService crmPermissionService;
    @Resource
    private CrmFollowUpRecordService followUpRecordService;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_LEADS_TYPE, subType = CRM_LEADS_CREATE_SUB_TYPE, bizNo = "{{#clue.id}}",
            success = CRM_LEADS_CREATE_SUCCESS)
    public Long createClue(CrmClueSaveReqVO createReqVO, Long userId) {
        // 1.1 Check associated data
        validateRelationDataExists(createReqVO);
        // 1.2 Check if the person in charge exists
        if (createReqVO.getOwnerUserId() != null) {
            adminUserApi.validateUserList(singletonList(createReqVO.getOwnerUserId()));
        } else {
            createReqVO.setOwnerUserId(userId); // If no person in charge is set, the default operator will be the person in charge
        }

        // 2. Insert
        CrmClueDO clue = BeanUtils.toBean(createReqVO, CrmClueDO.class).setId(null);
        clueMapper.insert(clue);

        // 3. Create data permissions
        CrmPermissionCreateReqBO createReqBO = new CrmPermissionCreateReqBO().setBizType(CrmBizTypeEnum.CRM_LEADS.getType())
                .setBizId(clue.getId()).setUserId(clue.getOwnerUserId()).setLevel(CrmPermissionLevelEnum.OWNER.getLevel());
        crmPermissionService.createPermission(createReqBO);

        // 4. Record operation log context
        LogRecordContext.putVariable("clue", clue);
        return clue.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_LEADS_TYPE, subType = CRM_LEADS_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = CRM_LEADS_UPDATE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_LEADS, bizId = "#updateReq.id", level = CrmPermissionLevelEnum.OWNER)
    public void updateClue(CrmClueSaveReqVO updateReq) {
        Assert.notNull(updateReq.getId(), "The clue number cannot be empty");
        // 1. Check if the clue exists
        CrmClueDO oldClue = validateClueExists(updateReq.getId());
        // 2. Verify associated data
        validateRelationDataExists(updateReq);

        // 3. Update
        CrmClueDO updateObj = BeanUtils.toBean(updateReq, CrmClueDO.class);
        clueMapper.updateById(updateObj);

        // 3. Record operation log context
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldClue, CrmCustomerSaveReqVO.class));
        LogRecordContext.putVariable("clueName", oldClue.getName());
    }

    @Override
    @LogRecord(type = CRM_LEADS_TYPE, subType = CRM_LEADS_UPDATE_SUB_TYPE, bizNo = "{{#updateReq.bizId}",
            success = CRM_LEADS_UPDATE_SUCCESS)
    public void updateClueFollowUp(CrmUpdateFollowUpReqBO updateReq) {
        // Check if the clue exists
        CrmClueDO oldClue = validateClueExists(updateReq.getBizId());

        // Update
        clueMapper.updateById(BeanUtils.toBean(updateReq, CrmClueDO.class).setId(updateReq.getBizId()));

        // 3. Record operation log context
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldClue, CrmUpdateFollowUpReqBO.class));
        LogRecordContext.putVariable("clueName", oldClue.getName());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_LEADS_TYPE, subType = CRM_LEADS_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_LEADS_DELETE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_LEADS, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void deleteClue(Long id) {
        // 1. Check existence
        CrmClueDO clue = validateClueExists(id);

        // 2. Delete
        clueMapper.deleteById(id);

        // 3. Delete data permission
        crmPermissionService.deletePermission(CrmBizTypeEnum.CRM_LEADS.getType(), id);

        // 4. Delete follow-up
        followUpRecordService.deleteFollowUpRecordByBiz(CrmBizTypeEnum.CRM_LEADS.getType(), id);

        // Record operation log context
        LogRecordContext.putVariable("clueName", clue.getName());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_LEADS_TYPE, subType = CRM_LEADS_TRANSFER_SUB_TYPE, bizNo = "{{#reqVO.id}}",
            success = CRM_LEADS_TRANSFER_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_LEADS, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void transferClue(CrmClueTransferReqVO reqVO, Long userId) {
        // 1 Check if the clue exists
        CrmClueDO clue = validateClueExists(reqVO.getId());

        // 2.1 Data permission transfer
        crmPermissionService.transferPermission(CrmClueConvert.INSTANCE.convert(reqVO, userId).setBizType(CrmBizTypeEnum.CRM_LEADS.getType()));
        // 2.2 Set a new person in charge
        clueMapper.updateOwnerUserIdById(reqVO.getId(), reqVO.getNewOwnerUserId());

        // 3. Record transfer log
        LogRecordContext.putVariable("clue", clue);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_LEADS, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void translateCustomer(CrmClueTranslateReqVO reqVO, Long userId) {
        // 1.1 Verification clues all exist
        Set<Long> clueIds = reqVO.getIds();
        List<CrmClueDO> clues = getClueList(clueIds, userId);
        if (CollUtil.isEmpty(clues) || ObjectUtil.notEqual(clues.size(), clueIds.size())) {
            clueIds.removeAll(convertSet(clues, CrmClueDO::getId));
            throw exception(CLUE_ANY_CLUE_NOT_EXISTS, clueIds);
        }
        // 1.2 There is already a transformation，Direct prompt。Avoid operation by users，Thought the conversion was successful
        List<CrmClueDO> translatedClues = filterList(clues,
                clue -> ObjectUtil.equal(Boolean.TRUE, clue.getTransformStatus()));
        if (CollUtil.isNotEmpty(translatedClues)) {
            throw exception(CLUE_ANY_CLUE_ALREADY_TRANSLATED, convertSet(translatedClues, CrmClueDO::getId));
        }

        // 2.1 Traverse the clues(Unconverted leads)，Create the corresponding customer
        clues.forEach(clue -> {
            Long customerId = customerService.createCustomer(BeanUtils.toBean(clue, CrmCustomerCreateReqBO.class), userId);
            clue.setCustomerId(customerId);
        });
        // 2.2 Update clues
        clueMapper.updateBatch(convertList(clues, clue -> new CrmClueDO().setId(clue.getId())
                .setTransformStatus(Boolean.TRUE).setCustomerId(clue.getCustomerId())));
        // 2.3 Copy follow-up records
        copyFollowUpRecords(clues);

        // 3. Record operation log
        for (CrmClueDO clue : clues) {
            getSelf().translateCustomerLog(clue);
        }
    }

    /**
     * After the lead is converted into a customer，The follow-up record of the clues needs to be recorded，Copy to client
     *
     * @param clues Converted clues
     */
    private void copyFollowUpRecords(List<CrmClueDO> clues) {
        List<CrmFollowUpRecordDO> followUpRecords = followUpRecordService.getFollowUpRecordByBiz(
                CrmBizTypeEnum.CRM_LEADS.getType(), convertSet(clues, CrmClueDO::getId));
        if (CollUtil.isEmpty(followUpRecords)) {
            return;
        }
        // Create follow-up
        Map<Long, CrmClueDO> clueMap = convertMap(clues, CrmClueDO::getId);
        followUpRecordService.createFollowUpRecordBatch(convertList(followUpRecords, followUpRecord ->
                BeanUtils.toBean(followUpRecord, CrmFollowUpCreateReqBO.class).setBizType(CrmBizTypeEnum.CRM_CUSTOMER.getType())
                        .setBizId(clueMap.get(followUpRecord.getBizId()).getCustomerId())));
    }

    @LogRecord(type = CRM_LEADS_TYPE, subType = CRM_LEADS_TRANSLATE_SUB_TYPE, bizNo = "{{#clue.id}}",
            success = CRM_LEADS_TRANSLATE_SUCCESS)
    public void translateCustomerLog(CrmClueDO clue) {
        // Record operation log context
        LogRecordContext.putVariable("clue", clue);
    }

    private void validateRelationDataExists(CrmClueSaveReqVO reqVO) {
        // Verification responsible person
        if (Objects.nonNull(reqVO.getOwnerUserId()) &&
                Objects.isNull(adminUserApi.getUser(reqVO.getOwnerUserId()))) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    private CrmClueDO validateClueExists(Long id) {
        CrmClueDO crmClueDO = clueMapper.selectById(id);
        if (crmClueDO == null) {
            throw exception(CLUE_NOT_EXISTS);
        }
        return crmClueDO;
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_LEADS, bizId = "#id", level = CrmPermissionLevelEnum.READ)
    public CrmClueDO getClue(Long id) {
        return clueMapper.selectById(id);
    }

    @Override
    public List<CrmClueDO> getClueList(Collection<Long> ids, Long userId) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return clueMapper.selectBatchIds(ids, userId);
    }

    @Override
    public PageResult<CrmClueDO> getCluePage(CrmCluePageReqVO pageReqVO, Long userId) {
        return clueMapper.selectPage(pageReqVO, userId);
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private CrmClueServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
