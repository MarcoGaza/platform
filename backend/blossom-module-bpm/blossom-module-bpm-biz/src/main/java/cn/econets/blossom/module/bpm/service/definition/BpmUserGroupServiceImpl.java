package cn.econets.blossom.module.bpm.service.definition;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.group.BpmUserGroupCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.group.BpmUserGroupPageReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.group.BpmUserGroupUpdateReqVO;
import cn.econets.blossom.module.bpm.convert.definition.BpmUserGroupConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmUserGroupDO;
import cn.econets.blossom.module.bpm.dal.mysql.definition.BpmUserGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.*;

/**
 * User Group Service Implementation class
 *
 * 
 */
@Service
@Validated
public class BpmUserGroupServiceImpl implements BpmUserGroupService {

    @Resource
    private BpmUserGroupMapper userGroupMapper;

    @Override
    public Long createUserGroup(BpmUserGroupCreateReqVO createReqVO) {
        // Insert
        BpmUserGroupDO userGroup = BpmUserGroupConvert.INSTANCE.convert(createReqVO);
        userGroupMapper.insert(userGroup);
        // Return
        return userGroup.getId();
    }

    @Override
    public void updateUserGroup(BpmUserGroupUpdateReqVO updateReqVO) {
        // Verify existence
        this.validateUserGroupExists(updateReqVO.getId());
        // Update
        BpmUserGroupDO updateObj = BpmUserGroupConvert.INSTANCE.convert(updateReqVO);
        userGroupMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserGroup(Long id) {
        // Verify existence
        this.validateUserGroupExists(id);
        // Delete
        userGroupMapper.deleteById(id);
    }

    private void validateUserGroupExists(Long id) {
        if (userGroupMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(USER_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public BpmUserGroupDO getUserGroup(Long id) {
        return userGroupMapper.selectById(id);
    }

    @Override
    public List<BpmUserGroupDO> getUserGroupList(Collection<Long> ids) {
        return userGroupMapper.selectBatchIds(ids);
    }


    @Override
    public List<BpmUserGroupDO> getUserGroupListByStatus(Integer status) {
        return userGroupMapper.selectListByStatus(status);
    }

    @Override
    public PageResult<BpmUserGroupDO> getUserGroupPage(BpmUserGroupPageReqVO pageReqVO) {
        return userGroupMapper.selectPage(pageReqVO);
    }

    @Override
    public void validUserGroups(Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // Get user group information
        List<BpmUserGroupDO> userGroups = userGroupMapper.selectBatchIds(ids);
        Map<Long, BpmUserGroupDO> userGroupMap = CollectionUtils.convertMap(userGroups, BpmUserGroupDO::getId);
        // Verification
        ids.forEach(id -> {
            BpmUserGroupDO userGroup = userGroupMap.get(id);
            if (userGroup == null) {
                throw ServiceExceptionUtil.exception(USER_GROUP_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(userGroup.getStatus())) {
                throw exception(USER_GROUP_IS_DISABLE, userGroup.getName());
            }
        });
    }

}
