package cn.econets.blossom.module.member.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.*;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.econets.blossom.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import cn.econets.blossom.module.member.controller.app.user.vo.*;
import cn.econets.blossom.module.member.convert.auth.AuthConvert;
import cn.econets.blossom.module.member.convert.user.MemberUserConvert;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.dal.mysql.user.MemberUserMapper;
import cn.econets.blossom.module.member.mq.producer.user.MemberUserProducer;
import cn.econets.blossom.module.system.api.sms.SmsCodeApi;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.econets.blossom.module.system.api.social.SocialClientApi;
import cn.econets.blossom.module.system.api.social.dto.SocialWxPhoneNumberInfoRespDTO;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.*;

/**
 * Member User Service Implementation class
 *
 * 
 */
@Service
@Valid
@Slf4j
public class MemberUserServiceImpl implements MemberUserService {

    @Resource
    private MemberUserMapper memberUserMapper;

    @Resource
    private SmsCodeApi smsCodeApi;

    @Resource
    private SocialClientApi socialClientApi;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private MemberUserProducer memberUserProducer;

    @Override
    public MemberUserDO getUserByMobile(String mobile) {
        return memberUserMapper.selectByMobile(mobile);
    }

    @Override
    public List<MemberUserDO> getUserListByNickname(String nickname) {
        return memberUserMapper.selectListByNicknameLike(nickname);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUserDO createUserIfAbsent(String mobile, String registerIp, Integer terminal) {
        // User already exists
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user != null) {
            return user;
        }
        // User does not exist，Then create
        return createUser(mobile, null, null, registerIp, terminal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUserDO createUser(String nickname, String avtar, String registerIp, Integer terminal) {
        return createUser(null, nickname, avtar, registerIp, terminal);
    }

    private MemberUserDO createUser(String mobile, String nickname, String avtar,
                                    String registerIp, Integer terminal) {
        // Generate password
        String password = IdUtil.fastSimpleUUID();
        // Insert user
        MemberUserDO user = new MemberUserDO();
        user.setMobile(mobile);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // Enabled by default
        user.setPassword(encodePassword(password)); // Encryption password
        user.setRegisterIp(registerIp).setRegisterTerminal(terminal);
        user.setNickname(nickname).setAvatar(avtar); // Basic Information
        if (StrUtil.isEmpty(nickname)) {
            // Nickname is Kongshi，A random name，Avoid some dependencies nickname Logic error，Or a little ugly。For example，When sending a text message with a nickname~
            user.setNickname("User" + RandomUtil.randomNumbers(6));
        }
        memberUserMapper.insert(user);

        // Send MQ Message：User creation
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                memberUserProducer.sendUserCreateMessage(user.getId());
            }

        });
        return user;
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        memberUserMapper.updateById(new MemberUserDO().setId(id)
                .setLoginIp(loginIp).setLoginDate(LocalDateTime.now()));
    }

    @Override
    public MemberUserDO getUser(Long id) {
        return memberUserMapper.selectById(id);
    }

    @Override
    public List<MemberUserDO> getUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return memberUserMapper.selectBatchIds(ids);
    }

    @Override
    public void updateUser(Long userId, AppMemberUserUpdateReqVO reqVO) {
        MemberUserDO updateObj = BeanUtils.toBean(reqVO, MemberUserDO.class).setId(userId);
        memberUserMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserMobile(Long userId, AppMemberUserUpdateMobileReqVO reqVO) {
        // 1.1 Check if the user exists
        MemberUserDO user = validateUserExists(userId);
        // 1.2 Check whether the new phone has been bound
        validateMobileUnique(null, reqVO.getMobile());

        // 2.1 Verify old phone and old verification code
        // Additional instructions：In terms of security，Verify old mobile phones as well oldCode Verification codes will be more secure。But due to uni-app The mall interface is not yet done，So there is no mandatory verification here
        if (StrUtil.isNotEmpty(reqVO.getOldCode())) {
            smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(user.getMobile()).setCode(reqVO.getOldCode())
                    .setScene(SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene()).setUsedIp(getClientIP()));
        }
        // 2.2 Use new verification code
        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(reqVO.getMobile()).setCode(reqVO.getCode())
                .setScene(SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene()).setUsedIp(getClientIP()));

        // 3. Update user's phone number
        memberUserMapper.updateById(MemberUserDO.builder().id(userId).mobile(reqVO.getMobile()).build());
    }

    @Override
    public void updateUserMobileByWeixin(Long userId, AppMemberUserUpdateMobileByWeixinReqVO reqVO) {
        // 1.1 Get the corresponding mobile phone number information
        SocialWxPhoneNumberInfoRespDTO phoneNumberInfo = socialClientApi.getWxMaPhoneNumberInfo(
                UserTypeEnum.MEMBER.getValue(), reqVO.getCode());
        Assert.notNull(phoneNumberInfo, "Failed to obtain mobile phone information，The result is empty");
        // 1.2 Check whether the new phone has been bound
        validateMobileUnique(userId, phoneNumberInfo.getPhoneNumber());

        // 2. Update user's phone number
        memberUserMapper.updateById(MemberUserDO.builder().id(userId).mobile(phoneNumberInfo.getPhoneNumber()).build());
    }

    @Override
    public void updateUserPassword(Long userId, AppMemberUserUpdatePasswordReqVO reqVO) {
        // Check if the user exists
        MemberUserDO user = validateUserExists(userId);
        // Verify verification code
        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(user.getMobile()).setCode(reqVO.getCode())
                .setScene(SmsSceneEnum.MEMBER_UPDATE_PASSWORD.getScene()).setUsedIp(getClientIP()));

        // Update user password
        memberUserMapper.updateById(MemberUserDO.builder().id(userId)
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    @Override
    public void resetUserPassword(AppMemberUserResetPasswordReqVO reqVO) {
        // Check if the user exists
        MemberUserDO user = validateUserExists(reqVO.getMobile());

        // Use verification code
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.MEMBER_RESET_PASSWORD,
                getClientIP()));

        // Update password
        memberUserMapper.updateById(MemberUserDO.builder().id(user.getId())
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    private MemberUserDO validateUserExists(String mobile) {
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }
        return user;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(MemberUserUpdateReqVO updateReqVO) {
        // Check existence
        validateUserExists(updateReqVO.getId());
        // Verify that the phone is unique
        validateMobileUnique(updateReqVO.getId(), updateReqVO.getMobile());

        // Update
        MemberUserDO updateObj = MemberUserConvert.INSTANCE.convert(updateReqVO);
        memberUserMapper.updateById(updateObj);
    }

    @VisibleForTesting
    MemberUserDO validateUserExists(Long id) {
        if (id == null) {
            return null;
        }
        MemberUserDO user = memberUserMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }

    @VisibleForTesting
    void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Users
        if (id == null) {
            throw exception(USER_MOBILE_USED, mobile);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_USED, mobile);
        }
    }

    @Override
    public PageResult<MemberUserDO> getUserPage(MemberUserPageReqVO pageReqVO) {
        return memberUserMapper.selectPage(pageReqVO);
    }

    @Override
    public void updateUserLevel(Long id, Long levelId, Integer experience) {
        // 0 Represents no level：PreventUpdateByIdTime，Questions that will be filtered out
        levelId = ObjectUtil.defaultIfNull(levelId, 0L);
        memberUserMapper.updateById(new MemberUserDO()
                .setId(id)
                .setLevelId(levelId).setExperience(experience)
        );
    }

    @Override
    public Long getUserCountByGroupId(Long groupId) {
        return memberUserMapper.selectCountByGroupId(groupId);
    }

    @Override
    public Long getUserCountByLevelId(Long levelId) {
        return memberUserMapper.selectCountByLevelId(levelId);
    }

    @Override
    public Long getUserCountByTagId(Long tagId) {
        return memberUserMapper.selectCountByTagId(tagId);
    }

    @Override
    public boolean updateUserPoint(Long id, Integer point) {
        if (point > 0) {
            memberUserMapper.updatePointIncr(id, point);
        } else if (point < 0) {
            return memberUserMapper.updatePointDecr(id, point) > 0;
        }
        return true;
    }

}
