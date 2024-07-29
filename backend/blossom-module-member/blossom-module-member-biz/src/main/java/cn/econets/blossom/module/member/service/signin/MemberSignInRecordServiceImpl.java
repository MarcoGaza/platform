package cn.econets.blossom.module.member.service.signin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.module.member.controller.admin.signin.vo.record.MemberSignInRecordPageReqVO;
import cn.econets.blossom.module.member.controller.app.signin.vo.record.AppMemberSignInRecordSummaryRespVO;
import cn.econets.blossom.module.member.convert.signin.MemberSignInRecordConvert;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInConfigDO;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInRecordDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.dal.mysql.signin.MemberSignInRecordMapper;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;
import cn.econets.blossom.module.member.enums.point.MemberPointBizTypeEnum;
import cn.econets.blossom.module.member.service.level.MemberLevelService;
import cn.econets.blossom.module.member.service.point.MemberPointRecordService;
import cn.econets.blossom.module.member.service.user.MemberUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.SIGN_IN_RECORD_TODAY_EXISTS;

/**
 * Sign-in record Service Implementation class
 *
 * 
 */
@Service
@Validated
public class MemberSignInRecordServiceImpl implements MemberSignInRecordService {

    @Resource
    private MemberSignInRecordMapper signInRecordMapper;
    @Resource
    private MemberSignInConfigService signInConfigService;
    @Resource
    private MemberPointRecordService pointRecordService;
    @Resource
    private MemberLevelService memberLevelService;

    @Resource
    private MemberUserService memberUserService;

    @Override
    public AppMemberSignInRecordSummaryRespVO getSignInRecordSummary(Long userId) {
        // 1. Initialize default return information
        AppMemberSignInRecordSummaryRespVO summary = new AppMemberSignInRecordSummaryRespVO();
        summary.setTotalDay(0);
        summary.setContinuousDay(0);
        summary.setTodaySignIn(false);

        // 2. Get the number of user check-in records
        Long signCount = signInRecordMapper.selectCountByUserId(userId);
        if (ObjUtil.equal(signCount, 0L)) {
            return summary;
        }
        summary.setTotalDay(signCount.intValue()); // Set the total number of sign-in days

        // 3. Check whether there is sign-in on the day
        MemberSignInRecordDO lastRecord = signInRecordMapper.selectLastRecordByUserId(userId);
        if (lastRecord == null) {
            return summary;
        }
        summary.setTodaySignIn(DateUtils.isToday(lastRecord.getCreateTime()));

        // 4.1 Check whether you are signed in today，Return directly if you don't sign in
        if (!summary.getTodaySignIn()) {
            return summary;
        }
        // 4.2 Number of consecutive sign-in days
        summary.setContinuousDay(lastRecord.getDay());
        return summary;
    }

    @Override
    public PageResult<MemberSignInRecordDO> getSignInRecordPage(MemberSignInRecordPageReqVO pageReqVO) {
        // Query users based on their nicknamesids
        Set<Long> userIds = null;
        if (StringUtils.isNotBlank(pageReqVO.getNickname())) {
            List<MemberUserDO> users = memberUserService.getUserListByNickname(pageReqVO.getNickname());
            // If the query result is empty, return directly without further query
            if (CollUtil.isEmpty(users)) {
                return PageResult.empty();
            }
            userIds = convertSet(users, MemberUserDO::getId);
        }
        // Paged query
        return signInRecordMapper.selectPage(pageReqVO, userIds);
    }

    @Override
    public PageResult<MemberSignInRecordDO> getSignRecordPage(Long userId, PageParam pageParam) {
        return signInRecordMapper.selectPage(userId, pageParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberSignInRecordDO createSignRecord(Long userId) {
        // 1. Get the current user's most recent check-in
        MemberSignInRecordDO lastRecord = signInRecordMapper.selectLastRecordByUserId(userId);
        // 1.1. Judge whether to sign in repeatedly
        validateSigned(lastRecord);

        // 2.1. Get all sign-in rules
        List<MemberSignInConfigDO> signInConfigs = signInConfigService.getSignInConfigList(CommonStatusEnum.ENABLE.getStatus());
        // 2.2. Combined data
        MemberSignInRecordDO record = MemberSignInRecordConvert.INSTANCE.convert(userId, lastRecord, signInConfigs);

        // 3. Insert sign-in record
        signInRecordMapper.insert(record);

        // 4. Increase points
        if (!ObjectUtils.equalsAny(record.getPoint(), null, 0)) {
            pointRecordService.createPointRecord(userId, record.getPoint(), MemberPointBizTypeEnum.SIGN, String.valueOf(record.getId()));
        }
        // 5. Increase experience
        if (!ObjectUtils.equalsAny(record.getExperience(), null, 0)) {
            memberLevelService.addExperience(userId, record.getExperience(), MemberExperienceBizTypeEnum.SIGN_IN, String.valueOf(record.getId()));
        }
        return record;
    }

    private void validateSigned(MemberSignInRecordDO signInRecordDO) {
        if (signInRecordDO == null) {
            return;
        }
        if (DateUtils.isToday(signInRecordDO.getCreateTime())) {
            throw exception(SIGN_IN_RECORD_TODAY_EXISTS);
        }
    }

}
