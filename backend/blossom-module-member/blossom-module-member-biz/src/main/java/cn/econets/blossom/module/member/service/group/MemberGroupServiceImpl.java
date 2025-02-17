package cn.econets.blossom.module.member.service.group;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupPageReqVO;
import cn.econets.blossom.module.member.controller.admin.group.vo.MemberGroupUpdateReqVO;
import cn.econets.blossom.module.member.convert.group.MemberGroupConvert;
import cn.econets.blossom.module.member.dal.dataobject.group.MemberGroupDO;
import cn.econets.blossom.module.member.dal.mysql.group.MemberGroupMapper;
import cn.econets.blossom.module.member.service.user.MemberUserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.GROUP_HAS_USER;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.GROUP_NOT_EXISTS;

/**
 * User Grouping Service Implementation class
 *
 * 
 */
@Service
@Validated
public class MemberGroupServiceImpl implements MemberGroupService {

    @Resource
    private MemberGroupMapper memberGroupMapper;

    @Resource
    private MemberUserService memberUserService;

    @Override
    public Long createGroup(MemberGroupCreateReqVO createReqVO) {
        // Insert
        MemberGroupDO group = MemberGroupConvert.INSTANCE.convert(createReqVO);
        memberGroupMapper.insert(group);
        // Return
        return group.getId();
    }

    @Override
    public void updateGroup(MemberGroupUpdateReqVO updateReqVO) {
        // Check existence
        validateGroupExists(updateReqVO.getId());
        // Update
        MemberGroupDO updateObj = MemberGroupConvert.INSTANCE.convert(updateReqVO);
        memberGroupMapper.updateById(updateObj);
    }

    @Override
    public void deleteGroup(Long id) {
        // Check existence
        validateGroupExists(id);
        // Check whether there are users under the group
        validateGroupHasUser(id);
        // Delete
        memberGroupMapper.deleteById(id);
    }

    void validateGroupExists(Long id) {
        if (memberGroupMapper.selectById(id) == null) {
            throw exception(GROUP_NOT_EXISTS);
        }
    }

    void validateGroupHasUser(Long id) {
        Long count = memberUserService.getUserCountByGroupId(id);
        if (count > 0) {
            throw exception(GROUP_HAS_USER);
        }
    }

    @Override
    public MemberGroupDO getGroup(Long id) {
        return memberGroupMapper.selectById(id);
    }

    @Override
    public List<MemberGroupDO> getGroupList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return memberGroupMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MemberGroupDO> getGroupPage(MemberGroupPageReqVO pageReqVO) {
        return memberGroupMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MemberGroupDO> getGroupListByStatus(Integer status) {
        return memberGroupMapper.selectListByStatus(status);
    }

}
