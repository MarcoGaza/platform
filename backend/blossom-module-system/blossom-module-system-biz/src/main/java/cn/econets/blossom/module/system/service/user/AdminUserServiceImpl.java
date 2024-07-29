package cn.econets.blossom.module.system.service.user;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.permission.core.util.DataPermissionUtils;
import cn.econets.blossom.module.infrastructure.api.file.FileApi;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserImportExcelVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserImportRespVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserPageReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.econets.blossom.module.system.dal.mysql.dept.UserPostMapper;
import cn.econets.blossom.module.system.dal.mysql.user.AdminUserMapper;
import cn.econets.blossom.module.system.dal.dataobject.dept.DeptDO;
import cn.econets.blossom.module.system.dal.dataobject.dept.UserPostDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.service.dept.DeptService;
import cn.econets.blossom.module.system.service.dept.PostService;
import cn.econets.blossom.module.system.service.permission.PermissionService;
import cn.econets.blossom.module.system.service.tenant.TenantService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.*;

/**
 * Backend User Service Implementation class
 *
 */
@Service("adminUserService")
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Value("${sys.user.init-password:123456}")
    private String userInitPassword;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    @Lazy // Delay，Avoid circular dependency errors
    private TenantService tenantService;

    @Resource
    private UserPostMapper userPostMapper;

    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserSaveReqVO createReqVO) {
        // Verify account coordination
        tenantService.handleTenantInfo(tenant -> {
            long count = userMapper.selectCount();
            if (count >= tenant.getAccountCount()) {
                throw exception(USER_COUNT_MAX, tenant.getAccountCount());
            }
        });
        // Verify correctness
        validateUserForCreateOrUpdate(null, createReqVO.getUsername(),
                createReqVO.getMobile(), createReqVO.getEmail(), createReqVO.getDeptId(), createReqVO.getPostIds());
        // Insert user
        AdminUserDO user = BeanUtils.toBean(createReqVO, AdminUserDO.class);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // Enabled by default
        user.setPassword(encodePassword(createReqVO.getPassword())); // Encryption password
        userMapper.insert(user);
        // Insert related positions
        if (CollectionUtil.isNotEmpty(user.getPostIds())) {
            userPostMapper.insertBatch(CollectionUtils.convertList(user.getPostIds(),
                    postId -> {
                        UserPostDO userPostDO = new UserPostDO();
                        userPostDO.setUserId(user.getId());
                        userPostDO.setPostId(postId);
                        return userPostDO;
                    }));
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserSaveReqVO updateReqVO) {
        updateReqVO.setPassword(null); // Special：Do not update password here
        // Verify correctness
        validateUserForCreateOrUpdate(updateReqVO.getId(), updateReqVO.getUsername(),
                updateReqVO.getMobile(), updateReqVO.getEmail(), updateReqVO.getDeptId(), updateReqVO.getPostIds());
        // Update User
        AdminUserDO updateObj = BeanUtils.toBean(updateReqVO, AdminUserDO.class);
        userMapper.updateById(updateObj);
        // Update position
        updateUserPost(updateReqVO, updateObj);
    }

    private void updateUserPost(UserSaveReqVO reqVO, AdminUserDO updateObj) {
        Long userId = reqVO.getId();
        Set<Long> dbPostIds = CollectionUtils.convertSet(userPostMapper.selectListByUserId(userId), UserPostDO::getPostId);
        // Calculate the number of new and deleted positions
        Set<Long> postIds = CollUtil.emptyIfNull(updateObj.getPostIds());
        Collection<Long> createPostIds = CollUtil.subtract(postIds, dbPostIds);
        Collection<Long> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        // Perform addition and deletion。For authorized menus，No need to do anything
        if (!CollectionUtil.isEmpty(createPostIds)) {
            userPostMapper.insertBatch(CollectionUtils.convertList(createPostIds,
                    postId -> {
                        UserPostDO userPostDO = new UserPostDO();
                        userPostDO.setUserId(userId);
                        userPostDO.setPostId(postId);
                        return userPostDO;
                    }));
        }
        if (!CollectionUtil.isEmpty(deletePostIds)) {
            userPostMapper.deleteByUserIdAndPostId(userId, deletePostIds);
        }
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        AdminUserDO adminUserDO = new AdminUserDO();
        adminUserDO.setId(id);
        adminUserDO.setLoginIp(loginIp);
        adminUserDO.setLoginDate(LocalDateTime.now());
        userMapper.updateById(adminUserDO);
    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateReqVO reqVO) {
        // Verify correctness
        validateUserExists(id);
        validateEmailUnique(id, reqVO.getEmail());
        validateMobileUnique(id, reqVO.getMobile());
        // Perform update
        AdminUserDO adminUserDO = BeanUtils.toBean(reqVO, AdminUserDO.class);
        adminUserDO.setId(id);
        userMapper.updateById(adminUserDO);
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        // Verify old password
        validateOldPassword(id, reqVO.getOldPassword());
        // Perform update
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(reqVO.getNewPassword())); // Encryption password
        userMapper.updateById(updateObj);
    }

    @Override
    public String updateUserAvatar(Long id, InputStream avatarFile) {
        validateUserExists(id);
        // Store file
        String avatar = fileApi.createFile(IoUtil.readBytes(avatarFile));
        // Update path
        AdminUserDO sysUserDO = new AdminUserDO();
        sysUserDO.setId(id);
        sysUserDO.setAvatar(avatar);
        userMapper.updateById(sysUserDO);
        return avatar;
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        // Verify user existence
        validateUserExists(id);
        // Update password
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setPassword(encodePassword(password)); // Encryption password
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        // Verify user existence
        validateUserExists(id);
        // Update status
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        userMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // Verify user existence
        validateUserExists(id);
        // Delete user
        userMapper.deleteById(id);
        // Delete user-related data
        permissionService.processUserDeleted(id);
        // Delete user position
        userPostMapper.deleteByUserId(id);
    }

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public AdminUserDO getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO) {
        return userMapper.selectPage(reqVO, getDeptCondition(reqVO.getDeptId()));
    }

    @Override
    public AdminUserDO getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<AdminUserDO> getUserListByDeptIds(Collection<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectListByDeptIds(deptIds);
    }

    @Override
    public List<AdminUserDO> getUserListByPostIds(Collection<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        Set<Long> userIds = CollectionUtils.convertSet(userPostMapper.selectListByPostIds(postIds), UserPostDO::getUserId);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<AdminUserDO> getUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public void validateUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // Get job information
        List<AdminUserDO> users = userMapper.selectBatchIds(ids);
        Map<Long, AdminUserDO> userMap = CollectionUtils.convertMap(users, AdminUserDO::getId);
        // Verification
        ids.forEach(id -> {
            AdminUserDO user = userMap.get(id);
            if (user == null) {
                throw exception(USER_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus())) {
                throw exception(USER_IS_DISABLE, user.getNickname());
            }
        });
    }

    @Override
    public List<AdminUserDO> getUserListByNickname(String nickname) {
        return userMapper.selectListByNickname(nickname);
    }

    /**
     * Get department conditions：Query the sub-department numbers of the specified department，Including itself
     * @param deptId Department Number
     * @return Department number collection
     */
    private Set<Long> getDeptCondition(Long deptId) {
        if (deptId == null) {
            return Collections.emptySet();
        }
        Set<Long> deptIds = CollectionUtils.convertSet(deptService.getChildDeptList(deptId), DeptDO::getId);
        deptIds.add(deptId); // Including itself
        return deptIds;
    }

    private void validateUserForCreateOrUpdate(Long id, String username, String mobile, String email,
                                               Long deptId, Set<Long> postIds) {
        // Close data permissions，Avoid lack of data permissions，No data found，This leads to incorrect unique verification
        DataPermissionUtils.executeIgnore(() -> {
            // Verify user existence
            validateUserExists(id);
            // Verify that the username is unique
            validateUsernameUnique(id, username);
            // Verify that the mobile phone number is unique
            validateMobileUnique(id, mobile);
            // Verify email uniqueness
            validateEmailUnique(id, email);
            // The verification department is open
            deptService.validateDeptList(CollectionUtils.singleton(deptId));
            // Verify that the position is open
            postService.validatePostList(postIds);
        });
    }

    @VisibleForTesting
    void validateUserExists(Long id) {
        if (id == null) {
            return;
        }
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        AdminUserDO user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Users
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    @VisibleForTesting
    void validateEmailUnique(Long id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        AdminUserDO user = userMapper.selectByEmail(email);
        if (user == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Users
        if (id == null) {
            throw exception(USER_EMAIL_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    @VisibleForTesting
    void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        AdminUserDO user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Users
        if (id == null) {
            throw exception(USER_MOBILE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    /**
     * Verify old password
     * @param id          User id
     * @param oldPassword Old password
     */
    @VisibleForTesting
    void validateOldPassword(Long id, String oldPassword) {
        AdminUserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // Add transaction，Rollback all imports if exception occurs
    public UserImportRespVO importUserList(List<UserImportExcelVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        UserImportRespVO respVO = UserImportRespVO.builder().createUsernames(new ArrayList<>())
                .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {
            // Verification，Judge whether there is a reason for non-compliance
            try {
                validateUserForCreateOrUpdate(null, null, importUser.getMobile(), importUser.getEmail(),
                        importUser.getDeptId(), null);
            } catch (ServiceException ex) {
                respVO.getFailureUsernames().put(importUser.getUsername(), ex.getMessage());
                return;
            }
            // Judge if it does not exist，Inserting
            AdminUserDO existUser = userMapper.selectByUsername(importUser.getUsername());
            if (existUser == null) {
                AdminUserDO adminUserDO = BeanUtils.toBean(importUser, AdminUserDO.class);
                adminUserDO.setPassword(encodePassword(userInitPassword));
                adminUserDO.setPostIds(new HashSet<>());
                userMapper.insert(adminUserDO); // Set the default password and empty position number array
                respVO.getCreateUsernames().add(importUser.getUsername());
                return;
            }
            // If exists，Judge whether to allow update
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getUsername(), USER_USERNAME_EXISTS.getMsg());
                return;
            }
            AdminUserDO updateUser = BeanUtils.toBean(importUser, AdminUserDO.class);
            updateUser.setId(existUser.getId());
            userMapper.updateById(updateUser);
            respVO.getUpdateUsernames().add(importUser.getUsername());
        });
        return respVO;
    }

    @Override
    public List<AdminUserDO> getUserListByStatus(Integer status) {
        return userMapper.selectListByStatus(status);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Encrypt the password
     *
     * @param password Password
     * @return Encrypted password
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
