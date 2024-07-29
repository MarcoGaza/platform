package cn.econets.blossom.module.system.service.permission;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.role.RoleSaveReqVO;
import cn.econets.blossom.module.system.dal.mysql.permission.RoleMapper;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleDO;
import cn.econets.blossom.module.system.dal.redis.RedisKeyConstants;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.enums.permission.DataScopeEnum;
import cn.econets.blossom.module.system.enums.permission.RoleCodeEnum;
import cn.econets.blossom.module.system.enums.permission.RoleTypeEnum;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * Role Service Implementation class
 *
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleSaveReqVO createReqVO, Integer type) {
        // Verify role
        validateRoleDuplicate(createReqVO.getName(), createReqVO.getCode(), null);
        // Insert into database
        RoleDO role = BeanUtils.toBean(createReqVO, RoleDO.class);
        role.setType(ObjectUtil.defaultIfNull(type, RoleTypeEnum.CUSTOM.getType()));
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        role.setDataScope(DataScopeEnum.ALL.getScope()); // All data can be viewed by default。The reason is，Some projects may not require project permissions
        roleMapper.insert(role);
        // Return
        return role.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.ROLE, key = "#updateReqVO.id")
    public void updateRole(RoleSaveReqVO updateReqVO) {
        // Check whether it can be updated
        validateRoleForUpdate(updateReqVO.getId());
        // Check whether the unique field of the role is repeated
        validateRoleDuplicate(updateReqVO.getName(), updateReqVO.getCode(), updateReqVO.getId());

        // Update to database
        RoleDO updateObj = BeanUtils.toBean(updateReqVO, RoleDO.class);
        roleMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.ROLE, key = "#id")
    public void updateRoleStatus(Long id, Integer status) {
        // Check whether it can be updated
        validateRoleForUpdate(id);

        // Update status
        RoleDO updateObj = new RoleDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        roleMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.ROLE, key = "#id")
    public void updateRoleDataScope(Long id, Integer dataScope, Set<Long> dataScopeDeptIds) {
        // Check whether it can be updated
        validateRoleForUpdate(id);

        // Update data range
        RoleDO updateObject = new RoleDO();
        updateObject.setId(id);
        updateObject.setDataScope(dataScope);
        updateObject.setDataScopeDeptIds(dataScopeDeptIds);
        roleMapper.updateById(updateObject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisKeyConstants.ROLE, key = "#id")
    public void deleteRole(Long id) {
        // Check whether it can be updated
        validateRoleForUpdate(id);
        // Mark for deletion
        roleMapper.deleteById(id);
        // Delete related data
        permissionService.processRoleDeleted(id);
    }

    /**
     * Check whether the unique field of the role is repeated
     *
     * 1. Does a character with the same name exist?
     * 2. Does a character with the same encoding exist?
     *
     * @param name Character name
     * @param code Role code
     * @param id Role number
     */
    @VisibleForTesting
    void validateRoleDuplicate(String name, String code, Long id) {
        // 0. Super Administrator，Not allowed to create
        if (RoleCodeEnum.isSuperAdmin(code)) {
            throw exception(ErrorCodeConstants.ROLE_ADMIN_CODE_ERROR, code);
        }
        // 1. This name The name is used by another character
        RoleDO role = roleMapper.selectByName(name);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ErrorCodeConstants.ROLE_NAME_DUPLICATE, name);
        }
        // 2. Does a character with the same encoding exist?
        if (!StringUtils.hasText(code)) {
            return;
        }
        // This code The code is used by another character
        role = roleMapper.selectByCode(code);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ErrorCodeConstants.ROLE_CODE_DUPLICATE, code);
        }
    }

    /**
     * Check whether the role can be updated
     *
     * @param id Role number
     */
    @VisibleForTesting
    void validateRoleForUpdate(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw exception(ErrorCodeConstants.ROLE_NOT_EXISTS);
        }
        // Built-in roles，Deletion is not allowed
        if (RoleTypeEnum.SYSTEM.getType().equals(roleDO.getType())) {
            throw exception(ErrorCodeConstants.ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    @Override
    public RoleDO getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.ROLE, key = "#id",
            unless = "#result == null")
    public RoleDO getRoleFromCache(Long id) {
        return roleMapper.selectById(id);
    }


    @Override
    public List<RoleDO> getRoleListByStatus(Collection<Integer> statuses) {
        return roleMapper.selectListByStatus(statuses);
    }

    @Override
    public List<RoleDO> getRoleList() {
        return roleMapper.selectList();
    }

    @Override
    public List<RoleDO> getRoleList(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleMapper.selectBatchIds(ids);
    }

    @Override
    public List<RoleDO> getRoleListFromCache(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // Used here for Loop to get from cache，Main consideration Spring CacheManager Issue of unable to perform batch operations
        RoleServiceImpl self = getSelf();
        return CollectionUtils.convertList(ids, self::getRoleFromCache);
    }

    @Override
    public PageResult<RoleDO> getRolePage(RolePageReqVO reqVO) {
        return roleMapper.selectPage(reqVO);
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        RoleServiceImpl self = getSelf();
        return ids.stream().anyMatch(id -> {
            RoleDO role = self.getRoleFromCache(id);
            return role != null && RoleCodeEnum.isSuperAdmin(role.getCode());
        });
    }

    @Override
    public void validateRoleList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // Get character information
        List<RoleDO> roles = roleMapper.selectBatchIds(ids);
        Map<Long, RoleDO> roleMap = CollectionUtils.convertMap(roles, RoleDO::getId);
        // Verification
        ids.forEach(id -> {
            RoleDO role = roleMap.get(id);
            if (role == null) {
                throw exception(ErrorCodeConstants.ROLE_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())) {
                throw exception(ErrorCodeConstants.ROLE_IS_DISABLE, role.getName());
            }
        });
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private RoleServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
