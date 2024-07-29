package cn.econets.blossom.module.bpm.service.definition;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.group.BpmUserGroupCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.group.BpmUserGroupPageReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.group.BpmUserGroupUpdateReqVO;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmUserGroupDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * User Group Service Interface
 *
 * 
 */
public interface BpmUserGroupService {

    /**
     * Create a user group
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createUserGroup(@Valid BpmUserGroupCreateReqVO createReqVO);

    /**
     * Update user group
     *
     * @param updateReqVO Update information
     */
    void updateUserGroup(@Valid BpmUserGroupUpdateReqVO updateReqVO);

    /**
     * Delete user group
     *
     * @param id Number
     */
    void deleteUserGroup(Long id);

    /**
     * Get user group
     *
     * @param id Number
     * @return User Group
     */
    BpmUserGroupDO getUserGroup(Long id);

    /**
     * Get user group list
     *
     * @param ids Number
     * @return User Group List
     */
    List<BpmUserGroupDO> getUserGroupList(Collection<Long> ids);

    /**
     * Get the user group list of the specified status
     *
     * @param status Status
     * @return User Group List
     */
    List<BpmUserGroupDO> getUserGroupListByStatus(Integer status);

    /**
     * Get user group paging
     *
     * @param pageReqVO Paged query
     * @return User Group Paging
     */
    PageResult<BpmUserGroupDO> getUserGroupPage(BpmUserGroupPageReqVO pageReqVO);

    /**
     * Check whether user groups are valid。The following situation，Deemed invalid：
     * 1. User group ID does not exist
     * 2. User group is disabled
     *
     * @param ids User group number array
     */
    void validUserGroups(Set<Long> ids);

}
