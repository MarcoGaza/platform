package cn.econets.blossom.module.member.service.group;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupPageReqVO;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupUpdateReqVO;
import cn.econets.blossom.module.member.dal.dataobject.group.MemberGroupDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * User Grouping Service Interface
 *
 * 
 */
public interface MemberGroupService {

    /**
     * Create user group
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createGroup(@Valid MemberGroupCreateReqVO createReqVO);

    /**
     * Update user group
     *
     * @param updateReqVO Update information
     */
    void updateGroup(@Valid MemberGroupUpdateReqVO updateReqVO);

    /**
     * Delete user group
     *
     * @param id Number
     */
    void deleteGroup(Long id);

    /**
     * Get user groups
     *
     * @param id Number
     * @return User Grouping
     */
    MemberGroupDO getGroup(Long id);

    /**
     * Get user group list
     *
     * @param ids Number
     * @return User group list
     */
    List<MemberGroupDO> getGroupList(Collection<Long> ids);

    /**
     * Get user group paging
     *
     * @param pageReqVO Paged query
     * @return User group paging
     */
    PageResult<MemberGroupDO> getGroupPage(MemberGroupPageReqVO pageReqVO);

    /**
     * Get the user group list of the specified status
     *
     * @param status Status
     * @return User group list
     */
    List<MemberGroupDO> getGroupListByStatus(Integer status);

    /**
     * Get the user group list in the enabled state
     *
     * @return User group list
     */
    default List<MemberGroupDO> getEnableGroupList() {
        return getGroupListByStatus(CommonStatusEnum.ENABLE.getStatus());
    }

}
