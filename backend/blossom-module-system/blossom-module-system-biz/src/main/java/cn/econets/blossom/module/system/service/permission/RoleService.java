package cn.econets.blossom.module.system.service.permission;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.role.RoleSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Role Service Interface
 *
 */
public interface RoleService {

    /**
     * Create a character
     *
     * @param createReqVO Create role information
     * @param type Role type
     * @return Role number
     */
    Long createRole(@Valid RoleSaveReqVO createReqVO, Integer type);

    /**
     * Update role
     *
     * @param updateReqVO Update role information
     */
    void updateRole(@Valid RoleSaveReqVO updateReqVO);

    /**
     * Delete role
     *
     * @param id Role number
     */
    void deleteRole(Long id);

    /**
     * Update character status
     *
     * @param id Role number
     * @param status Status
     */
    void updateRoleStatus(Long id, Integer status);

    /**
     * Set the role's data permissions
     *
     * @param id Role number
     * @param dataScope Data Range
     * @param dataScopeDeptIds Department number array
     */
    void updateRoleDataScope(Long id, Integer dataScope, Set<Long> dataScopeDeptIds);

    /**
     * Get the role
     *
     * @param id Character number
     * @return Role
     */
    RoleDO getRole(Long id);

    /**
     * Get the role，From cache
     *
     * @param id Role number
     * @return Role
     */
    RoleDO getRoleFromCache(Long id);

    /**
     * Get the role list
     *
     * @param ids Role number array
     * @return Role List
     */
    List<RoleDO> getRoleList(Collection<Long> ids);

    /**
     * Get role array，From cache
     *
     * @param ids Role number array
     * @return Role array
     */
    List<RoleDO> getRoleListFromCache(Collection<Long> ids);

    /**
     * Get the role list
     *
     * @param statuses Filter status
     * @return Role List
     */
    List<RoleDO> getRoleListByStatus(Collection<Integer> statuses);

    /**
     * Get a list of all roles
     *
     * @return Role List
     */
    List<RoleDO> getRoleList();

    /**
     * Get the role page
     *
     * @param reqVO Role paging query
     * @return Role paging results
     */
    PageResult<RoleDO> getRolePage(RolePageReqVO reqVO);

    /**
     * Judge the character number array，Is there an administrator?
     *
     * @param ids Role number array
     * @return Is there an administrator?
     */
    boolean hasAnySuperAdmin(Collection<Long> ids);

    /**
     * Check if the characters are valid。As follows，Deemed invalid：
     * 1. Role number does not exist
     * 2. The character is disabled
     *
     * @param ids Role number array
     */
    void validateRoleList(Collection<Long> ids);

}
