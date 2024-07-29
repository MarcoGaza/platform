package cn.econets.blossom.module.system.api.user;

import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import cn.econets.blossom.module.system.dal.dataobject.dept.DeptDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.service.dept.DeptService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;


/**
 * Admin User API Implementation class
 *
 */
@Service
public class AdminUserApiImpl implements AdminUserApi {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;

    @Override
    public AdminUserRespDTO getUser(Long id) {
        AdminUserDO user = userService.getUser(id);
        return BeanUtils.toBean(user, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserListBySubordinate(Long userId) {
        // 1.1 Get the department the user is responsible for
        AdminUserDO user = userService.getUser(userId);
        if (user == null) {
            return Collections.emptyList();
        }
        ArrayList<Long> deptIds = new ArrayList<>();
        DeptDO dept = deptService.getDept(user.getDeptId());
        if (dept == null) {
            return Collections.emptyList();
        }
        if (ObjUtil.notEqual(dept.getLeaderUserId(), userId)) { // Verify as responsible person
            return Collections.emptyList();
        }
        deptIds.add(dept.getId());
        // 1.2 Get all sub-departments
        List<DeptDO> childDeptList = deptService.getChildDeptList(dept.getId());
        if (CollUtil.isNotEmpty(childDeptList)) {
            deptIds.addAll(convertSet(childDeptList, DeptDO::getId));
        }

        // 2. Get the user information corresponding to the department
        List<AdminUserDO> users = userService.getUserListByDeptIds(deptIds);
        users.removeIf(item -> ObjUtil.equal(item.getId(), userId)); // Exclude yourself
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserList(Collection<Long> ids) {
        List<AdminUserDO> users = userService.getUserList(ids);
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserListByDeptIds(Collection<Long> deptIds) {
        List<AdminUserDO> users = userService.getUserListByDeptIds(deptIds);
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public List<AdminUserRespDTO> getUserListByPostIds(Collection<Long> postIds) {
        List<AdminUserDO> users = userService.getUserListByPostIds(postIds);
        return BeanUtils.toBean(users, AdminUserRespDTO.class);
    }

    @Override
    public void validateUserList(Collection<Long> ids) {
        userService.validateUserList(ids);
    }

}
