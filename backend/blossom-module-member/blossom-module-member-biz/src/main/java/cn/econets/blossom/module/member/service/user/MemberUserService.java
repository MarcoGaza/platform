package cn.econets.blossom.module.member.service.user;

import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.econets.blossom.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import cn.econets.blossom.module.member.controller.app.user.vo.*;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Member User Service Interface
 *
 * 
 */
public interface MemberUserService {

    /**
     * Query users via mobile phone
     *
     * @param mobile Mobile phone
     * @return User object
     */
    MemberUserDO getUserByMobile(String mobile);

    /**
     * Based on user nickname，Fuzzy matching user list
     *
     * @param nickname User Nickname，Fuzzy matching
     * @return List of user information
     */
    List<MemberUserDO> getUserListByNickname(String nickname);

    /**
     * Create a user based on a mobile phone number。
     * If the user already exists，Return directly
     *
     * @param mobile     Mobile phone number
     * @param registerIp Register IP
     * @param terminal   Terminal {@link TerminalEnum}
     * @return User object
     */
    MemberUserDO createUserIfAbsent(@Mobile String mobile, String registerIp, Integer terminal);

    /**
     * Create user
     * Purpose：When logging in with three parties，If the user is not bound，Automatically create corresponding users
     *
     * @param nickname   Nickname
     * @param avtar      Avatar
     * @param registerIp Register IP
     * @param terminal   Terminal {@link TerminalEnum}
     * @return User object
     */
    MemberUserDO createUser(String nickname, String avtar, String registerIp, Integer terminal);

    /**
     * Update the user's last login information
     *
     * @param id      User Number
     * @param loginIp Login IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * By user ID Query user
     *
     * @param id UserID
     * @return User object information
     */
    MemberUserDO getUser(Long id);

    /**
     * By user ID Query users
     *
     * @param ids User ID
     * @return User object information array
     */
    List<MemberUserDO> getUserList(Collection<Long> ids);

    /**
     * 【Member】Modify basic information
     *
     * @param userId User Number
     * @param reqVO  Basic Information
     */
    void updateUser(Long userId, AppMemberUserUpdateReqVO reqVO);

    /**
     * 【Member】Modify mobile phone，Based on mobile phone verification code
     *
     * @param userId User Number
     * @param reqVO  Request information
     */
    void updateUserMobile(Long userId, AppMemberUserUpdateMobileReqVO reqVO);

    /**
     * 【Member】Modify mobile phone，Authorization code based on WeChat applet
     *
     * @param userId User Number
     * @param reqVO Request information
     */
    void updateUserMobileByWeixin(Long userId, AppMemberUserUpdateMobileByWeixinReqVO reqVO);

    /**
     * 【Member】Change password
     *
     * @param userId User Number
     * @param reqVO  Request information
     */
    void updateUserPassword(Long userId, AppMemberUserUpdatePasswordReqVO reqVO);

    /**
     * 【Member】Forgot password
     *
     * @param reqVO Request information
     */
    void resetUserPassword(AppMemberUserResetPasswordReqVO reqVO);

    /**
     * Judge whether the password matches
     *
     * @param rawPassword     Unencrypted password
     * @param encodedPassword Encrypted password
     * @return Whether it matches
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

    /**
     * 【Administrator】Update member user
     *
     * @param updateReqVO Update information
     */
    void updateUser(@Valid MemberUserUpdateReqVO updateReqVO);

    /**
     * 【Administrator】Get member user paging
     *
     * @param pageReqVO Paged query
     * @return Member User Paging
     */
    PageResult<MemberUserDO> getUserPage(MemberUserPageReqVO pageReqVO);

    /**
     * Update user's level and experience
     *
     * @param id         User Number
     * @param levelId    User Level
     * @param experience User Experience
     */
    void updateUserLevel(Long id, Long levelId, Integer experience);

    /**
     * Get the number of users in a specified user group
     *
     * @param groupId User group number
     * @return Number of users
     */
    Long getUserCountByGroupId(Long groupId);

    /**
     * Get the number of users at a specified user level
     *
     * @param levelId User level number
     * @return Number of users
     */
    Long getUserCountByLevelId(Long levelId);

    /**
     * Get the number of users under the specified member tag
     *
     * @param tagId User tag number
     * @return Number of users
     */
    Long getUserCountByTagId(Long tagId);

    /**
     * Update user's points
     *
     * @param userId User Number
     * @param point  Number of points
     * @return Update results
     */
    boolean updateUserPoint(Long userId, Integer point);

}
