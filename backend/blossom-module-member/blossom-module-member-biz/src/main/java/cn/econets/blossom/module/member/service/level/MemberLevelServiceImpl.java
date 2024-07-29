package cn.econets.blossom.module.member.service.level;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.module.member.controller.admin.level.vo.level.MemberLevelCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.level.vo.level.MemberLevelListReqVO;
import cn.econets.blossom.module.member.controller.admin.level.vo.level.MemberLevelUpdateReqVO;
import cn.econets.blossom.module.member.controller.admin.user.vo.MemberUserUpdateLevelReqVO;
import cn.econets.blossom.module.member.convert.level.MemberLevelConvert;
import cn.econets.blossom.module.member.convert.level.MemberLevelRecordConvert;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelDO;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelRecordDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.dal.mysql.level.MemberLevelMapper;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;
import cn.econets.blossom.module.member.service.user.MemberUserService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.*;

/**
 * Member Level Service Implementation class
 *
 * 
 */
@Slf4j
@Service
@Validated
public class MemberLevelServiceImpl implements MemberLevelService {

    @Resource
    private MemberLevelMapper memberLevelMapper;

    @Resource
    private MemberLevelRecordService memberLevelRecordService;
    @Resource
    private MemberExperienceRecordService memberExperienceRecordService;
    @Resource
    private MemberUserService memberUserService;

    @Override
    public Long createLevel(MemberLevelCreateReqVO createReqVO) {
        // Check whether the configuration is valid
        validateConfigValid(null, createReqVO.getName(), createReqVO.getLevel(), createReqVO.getExperience());

        // Insert
        MemberLevelDO level = MemberLevelConvert.INSTANCE.convert(createReqVO);
        memberLevelMapper.insert(level);
        // Return
        return level.getId();
    }

    @Override
    public void updateLevel(MemberLevelUpdateReqVO updateReqVO) {
        // Check existence
        validateLevelExists(updateReqVO.getId());
        // Check whether the configuration is valid
        validateConfigValid(updateReqVO.getId(), updateReqVO.getName(), updateReqVO.getLevel(), updateReqVO.getExperience());

        // Update
        MemberLevelDO updateObj = MemberLevelConvert.INSTANCE.convert(updateReqVO);
        memberLevelMapper.updateById(updateObj);
    }

    @Override
    public void deleteLevel(Long id) {
        // Check existence
        validateLevelExists(id);
        // Check whether there are users under the group
        validateLevelHasUser(id);
        // Delete
        memberLevelMapper.deleteById(id);
    }

    @VisibleForTesting
    MemberLevelDO validateLevelExists(Long id) {
        MemberLevelDO levelDO = memberLevelMapper.selectById(id);
        if (levelDO == null) {
            throw exception(LEVEL_NOT_EXISTS);
        }
        return levelDO;
    }

    @VisibleForTesting
    void validateNameUnique(List<MemberLevelDO> list, Long id, String name) {
        for (MemberLevelDO levelDO : list) {
            if (ObjUtil.notEqual(levelDO.getName(), name)) {
                continue;
            }
            if (id == null || !id.equals(levelDO.getId())) {
                throw exception(LEVEL_NAME_EXISTS, levelDO.getName());
            }
        }
    }

    @VisibleForTesting
    void validateLevelUnique(List<MemberLevelDO> list, Long id, Integer level) {
        for (MemberLevelDO levelDO : list) {
            if (ObjUtil.notEqual(levelDO.getLevel(), level)) {
                continue;
            }

            if (id == null || !id.equals(levelDO.getId())) {
                throw exception(LEVEL_VALUE_EXISTS, levelDO.getLevel(), levelDO.getName());
            }
        }
    }

    @VisibleForTesting
    void validateExperienceOutRange(List<MemberLevelDO> list, Long id, Integer level, Integer experience) {
        for (MemberLevelDO levelDO : list) {
            if (levelDO.getId().equals(id)) {
                continue;
            }

            if (levelDO.getLevel() < level) {
                // Experience is greater than the previous level
                if (experience <= levelDO.getExperience()) {
                    throw exception(LEVEL_EXPERIENCE_MIN, levelDO.getName(), levelDO.getExperience());
                }
            } else if (levelDO.getLevel() > level) {
                //Smaller than the next level
                if (experience >= levelDO.getExperience()) {
                    throw exception(LEVEL_EXPERIENCE_MAX, levelDO.getName(), levelDO.getExperience());
                }
            }
        }
    }

    @VisibleForTesting
    void validateConfigValid(Long id, String name, Integer level, Integer experience) {
        List<MemberLevelDO> list = memberLevelMapper.selectList();
        // Verify name uniqueness
        validateNameUnique(list, id, name);
        // Verification level unique
        validateLevelUnique(list, id, level);
        // Check whether the experience required for upgrading is valid: Greater than the previous level，Smaller than the next level
        validateExperienceOutRange(list, id, level, experience);
    }

    @VisibleForTesting
    void validateLevelHasUser(Long id) {
        Long count = memberUserService.getUserCountByLevelId(id);
        if (count > 0) {
            throw exception(LEVEL_HAS_USER);
        }
    }

    @Override
    public MemberLevelDO getLevel(Long id) {
        return id != null && id > 0 ? memberLevelMapper.selectById(id) : null;
    }

    @Override
    public List<MemberLevelDO> getLevelList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return memberLevelMapper.selectBatchIds(ids);
    }

    @Override
    public List<MemberLevelDO> getLevelList(MemberLevelListReqVO listReqVO) {
        return memberLevelMapper.selectList(listReqVO);
    }

    @Override
    public List<MemberLevelDO> getLevelListByStatus(Integer status) {
        return memberLevelMapper.selectListByStatus(status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserLevel(MemberUserUpdateLevelReqVO updateReqVO) {
        MemberUserDO user = memberUserService.getUser(updateReqVO.getId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        // Level has not changed
        if (ObjUtil.equal(user.getLevelId(), updateReqVO.getLevelId())) {
            return;
        }

        // 1. Record level changes
        MemberLevelRecordDO levelRecord = new MemberLevelRecordDO()
                .setUserId(user.getId()).setRemark(updateReqVO.getReason());
        MemberLevelDO memberLevel = null;
        if (updateReqVO.getLevelId() == null) {
            // When canceling a user level，Need to deduct experience
            levelRecord.setExperience(-user.getExperience());
            levelRecord.setUserExperience(0);
            levelRecord.setDescription("The administrator canceled the level");
        } else {
            // Copy level configuration
            memberLevel = validateLevelExists(updateReqVO.getLevelId());
            MemberLevelRecordConvert.INSTANCE.copyTo(memberLevel, levelRecord);
            // Change Experience Value = Level Upgrade Experience - Member's current experience；Positive numbers increase experience，Negative numbers deduct experience points
            levelRecord.setExperience(memberLevel.getExperience() - user.getExperience());
            levelRecord.setUserExperience(memberLevel.getExperience()); // Member's current experience = Level Upgrade Experience
            levelRecord.setDescription("Administrator adjusted to：" + memberLevel.getName());
        }
        memberLevelRecordService.createLevelRecord(levelRecord);

        // 2. Record member experience changes
        memberExperienceRecordService.createExperienceRecord(user.getId(),
                levelRecord.getExperience(), levelRecord.getUserExperience(),
                MemberExperienceBizTypeEnum.ADMIN, String.valueOf(MemberExperienceBizTypeEnum.ADMIN.getType()));

        // 3. Update the level number on the membership table、Experience Value
        memberUserService.updateUserLevel(user.getId(), updateReqVO.getLevelId(),
                levelRecord.getUserExperience());

        // 4. Send level change messages to members
        notifyMemberLevelChange(user.getId(), memberLevel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExperience(Long userId, Integer experience, MemberExperienceBizTypeEnum bizType, String bizId) {
        if (experience == 0) {
            return;
        }
        if (!bizType.isAdd() && experience > 0) {
            experience = -experience;
        }

        // 1. Create experience record
        MemberUserDO user = memberUserService.getUser(userId);
        Integer userExperience = ObjUtil.defaultIfNull(user.getExperience(), 0);
        userExperience = NumberUtil.max(userExperience + experience, 0); // Prevent negative deductions
        MemberLevelRecordDO levelRecord = new MemberLevelRecordDO()
                .setUserId(user.getId())
                .setExperience(experience)
                .setUserExperience(userExperience);
        memberExperienceRecordService.createExperienceRecord(userId, experience, userExperience,
                bizType, bizId);

        // 2.1 Save level change records
        MemberLevelDO newLevel = calculateNewLevel(user, userExperience);
        if (newLevel != null) {
            MemberLevelRecordConvert.INSTANCE.copyTo(newLevel, levelRecord);
            memberLevelRecordService.createLevelRecord(levelRecord);

            // 2.2 Send level change messages to members
            notifyMemberLevelChange(userId, newLevel);
        }

        // 3. Update the level number on the membership table、Experience Value
        memberUserService.updateUserLevel(user.getId(), Optional.ofNullable(levelRecord.getLevelId()).orElse(user.getLevelId()), userExperience);
    }

    /**
     * Calculate member level
     *
     * @param user           Member
     * @param userExperience Current experience value of the member
     * @return New level of membership，nullNo change
     */
    private MemberLevelDO calculateNewLevel(MemberUserDO user, int userExperience) {
        List<MemberLevelDO> list = getEnableLevelList();
        if (CollUtil.isEmpty(list)) {
            log.warn("Failed to calculate member level：Member level configuration does not exist");
            return null;
        }

        MemberLevelDO matchLevel = list.stream()
                .filter(level -> userExperience >= level.getExperience())
                .max(Comparator.nullsFirst(Comparator.comparing(MemberLevelDO::getLevel)))
                .orElse(null);
        if (matchLevel == null) {
            log.warn("Failed to calculate member level：No member found{}Experience{}Corresponding level configuration", user.getId(), userExperience);
            return null;
        }

        // Level has not changed
        if (ObjectUtil.equal(matchLevel.getId(), user.getLevelId())) {
            return null;
        }

        return matchLevel;
    }

    private void notifyMemberLevelChange(Long userId, MemberLevelDO level) {
        //todo: Send a message to members
    }

}
