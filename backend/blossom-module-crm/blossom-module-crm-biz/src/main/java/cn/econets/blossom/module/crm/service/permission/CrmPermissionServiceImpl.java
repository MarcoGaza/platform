package cn.econets.blossom.module.crm.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.permission.vo.CrmPermissionUpdateReqVO;
import cn.econets.blossom.module.crm.convert.permission.CrmPermissionConvert;
import cn.econets.blossom.module.crm.dal.dataobject.permission.CrmPermissionDO;
import cn.econets.blossom.module.crm.dal.mysql.permission.CrmPermissionMapper;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.util.CrmPermissionUtils;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionCreateReqBO;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionTransferReqBO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.anyMatch;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum.isOwner;

/**
 * CRM Data permissions Service Interface implementation class
 *
 */
@Service
@Validated
public class CrmPermissionServiceImpl implements CrmPermissionService {

    @Resource
    private CrmPermissionMapper permissionMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPermission(CrmPermissionCreateReqBO createReqBO) {
        validatePermissionNotExists(Collections.singletonList(createReqBO));
        // 1. Check if the user exists
        adminUserApi.validateUserList(Collections.singletonList(createReqBO.getUserId()));

        // 2. Create
        CrmPermissionDO permission = BeanUtils.toBean(createReqBO, CrmPermissionDO.class);
        permissionMapper.insert(permission);
        return permission.getId();
    }

    @Override
    public void createPermissionBatch(List<CrmPermissionCreateReqBO> createReqBOs) {
        validatePermissionNotExists(createReqBOs);
        // 1. Check if the user exists
        adminUserApi.validateUserList(convertSet(createReqBOs, CrmPermissionCreateReqBO::getUserId));

        // 2. Create
        List<CrmPermissionDO> permissions = BeanUtils.toBean(createReqBOs, CrmPermissionDO.class);
        permissionMapper.insertBatch(permissions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(CrmPermissionUpdateReqVO updateReqVO) {
        // 1. Check existence
        validatePermissionExists(updateReqVO.getIds());
        // 2. Update
        List<CrmPermissionDO> updateDO = CrmPermissionConvert.INSTANCE.convertList(updateReqVO);
        permissionMapper.updateBatch(updateDO);
    }

    private void validatePermissionExists(Collection<Long> ids) {
        List<CrmPermissionDO> permissionList = permissionMapper.selectBatchIds(ids);
        if (ObjUtil.notEqual(permissionList.size(), ids.size())) {
            throw exception(CRM_PERMISSION_NOT_EXISTS);
        }
    }

    private void validatePermissionNotExists(Collection<CrmPermissionCreateReqBO> createReqBOs) {
        Set<Integer> bizTypes = convertSet(createReqBOs, CrmPermissionCreateReqBO::getBizType);
        Set<Long> bizIds = convertSet(createReqBOs, CrmPermissionCreateReqBO::getBizId);
        Set<Long> userIds = convertSet(createReqBOs, CrmPermissionCreateReqBO::getUserId);
        Long count = permissionMapper.selectListByBiz(bizTypes, bizIds, userIds);
        if (count > 0) {
            throw exception(CRM_PERMISSION_CREATE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferPermission(CrmPermissionTransferReqBO transferReqBO) {
        // 1. Verify data permissions：Is he the person in charge?，Only the person in charge can transfer
        CrmPermissionDO oldPermission = permissionMapper.selectByBizTypeAndBizIdByUserId(
                transferReqBO.getBizType(), transferReqBO.getBizId(), transferReqBO.getUserId());
        String bizTypeName = CrmBizTypeEnum.getNameByType(transferReqBO.getBizType());
        if (oldPermission == null // Not the owner，And not a super administrator
                || (!isOwner(oldPermission.getLevel()) && !CrmPermissionUtils.isCrmAdmin())) {
            throw exception(CRM_PERMISSION_DENIED, bizTypeName);
        }
        // 1.1 Check whether the transferee is already the person in charge
        if (ObjUtil.equal(transferReqBO.getNewOwnerUserId(), oldPermission.getUserId())) {
            throw exception(CRM_PERMISSION_MODEL_TRANSFER_FAIL_OWNER_USER_EXISTS, bizTypeName);
        }
        // 1.2 Check if the new person in charge exists
        adminUserApi.validateUserList(Collections.singletonList(transferReqBO.getNewOwnerUserId()));

        // 2. Modify the permissions of the new person in charge
        List<CrmPermissionDO> permissions = permissionMapper.selectByBizTypeAndBizId(
                transferReqBO.getBizType(), transferReqBO.getBizId()); // Get all data permissions
        CrmPermissionDO permission = CollUtil.findOne(permissions,
                item -> ObjUtil.equal(item.getUserId(), transferReqBO.getNewOwnerUserId()));
        if (permission == null) {
            permissionMapper.insert(new CrmPermissionDO().setBizType(transferReqBO.getBizType())
                    .setBizId(transferReqBO.getBizId()).setUserId(transferReqBO.getNewOwnerUserId())
                    .setLevel(CrmPermissionLevelEnum.OWNER.getLevel()));
        } else {
            permissionMapper.updateById(new CrmPermissionDO().setId(permission.getId())
                    .setLevel(CrmPermissionLevelEnum.OWNER.getLevel()));
        }

        // 3. Modify the authority of the old person in charge
        if (transferReqBO.getOldOwnerPermissionLevel() != null) {
            permissionMapper.updateById(new CrmPermissionDO().setId(oldPermission.getId())
                    .setLevel(transferReqBO.getOldOwnerPermissionLevel()));
        } else {
            permissionMapper.deleteById(oldPermission.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Integer bizType, Long bizId, Integer level) {
        // Check existence
        List<CrmPermissionDO> permissions = permissionMapper.selectListByBizTypeAndBizIdAndLevel(
                bizType, bizId, level);
        if (CollUtil.isEmpty(permissions)) {
            throw exception(CRM_PERMISSION_NOT_EXISTS);
        }

        // Delete data permission
        permissionMapper.deleteBatchIds(convertSet(permissions, CrmPermissionDO::getId));
    }

    @Override
    public void deletePermission(Integer bizType, Long bizId) {
        int deletedCount = permissionMapper.deletePermission(bizType, bizId);
        if (deletedCount == 0) {
            throw exception(CRM_PERMISSION_NOT_EXISTS);
        }
    }

    @Override
    public void deletePermissionBatch(Collection<Long> ids, Long userId) {
        List<CrmPermissionDO> permissions = permissionMapper.selectBatchIds(ids);
        if (CollUtil.isEmpty(permissions)) {
            throw exception(CRM_PERMISSION_NOT_EXISTS);
        }
        // Verification：The module data numbers of data permissions are consistent and it is impossible for two to exist
        if (convertSet(permissions, CrmPermissionDO::getBizId).size() > 1) {
            throw exception(CRM_PERMISSION_DELETE_FAIL);
        }
        // Check whether the operator is the responsible person
        CrmPermissionDO permission = permissionMapper.selectByBizIdAndUserId(permissions.get(0).getBizId(), userId);
        if (permission == null) {
            throw exception(CRM_PERMISSION_DELETE_DENIED);
        }
        if (!CrmPermissionLevelEnum.isOwner(permission.getLevel())) {
            throw exception(CRM_PERMISSION_DELETE_DENIED);
        }

        // Delete data permission
        permissionMapper.deleteBatchIds(ids);
    }

    @Override
    public void deleteSelfPermission(Long id, Long userId) {
        // Verify that the data exists and is your own
        CrmPermissionDO permission = permissionMapper.selectByIdAndUserId(id, userId);
        if (permission == null) {
            throw exception(CRM_PERMISSION_NOT_EXISTS);
        }
        // Check if you are the person in charge
        if (CrmPermissionLevelEnum.isOwner(permission.getLevel())) {
            throw exception(CRM_PERMISSION_DELETE_SELF_PERMISSION_FAIL_EXIST_OWNER);
        }

        // Delete
        permissionMapper.deleteById(id);
    }

    @Override
    public List<CrmPermissionDO> getPermissionListByBiz(Integer bizType, Long bizId) {
        return permissionMapper.selectByBizTypeAndBizId(bizType, bizId);
    }

    @Override
    public List<CrmPermissionDO> getPermissionListByBiz(Integer bizType, Collection<Long> bizIds) {
        return permissionMapper.selectByBizTypeAndBizIds(bizType, bizIds);
    }

    @Override
    public List<CrmPermissionDO> getPermissionListByBizTypeAndUserId(Integer bizType, Long userId) {
        return permissionMapper.selectListByBizTypeAndUserId(bizType, userId);
    }

    @Override
    public boolean hasPermission(Integer bizType, Long bizId, Long userId, CrmPermissionLevelEnum level) {
        List<CrmPermissionDO> permissionList = permissionMapper.selectByBizTypeAndBizId(bizType, bizId);
        return anyMatch(permissionList, permission ->
                ObjUtil.equal(permission.getUserId(), userId) && ObjUtil.equal(permission.getLevel(), level.getLevel()));
    }

}
