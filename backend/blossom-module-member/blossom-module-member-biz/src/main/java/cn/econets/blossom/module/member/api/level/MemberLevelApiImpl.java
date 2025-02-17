package cn.econets.blossom.module.member.api.level;

import cn.econets.blossom.module.member.api.level.dto.MemberLevelRespDTO;
import cn.econets.blossom.module.member.convert.level.MemberLevelConvert;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;
import cn.econets.blossom.module.member.service.level.MemberLevelService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.EXPERIENCE_BIZ_NOT_SUPPORT;

/**
 * Member Level API Implementation class
 *
 * 
 */
@Service
@Validated
public class MemberLevelApiImpl implements MemberLevelApi {

    @Resource
    private MemberLevelService memberLevelService;

    @Override
    public MemberLevelRespDTO getMemberLevel(Long id) {
        return MemberLevelConvert.INSTANCE.convert02(memberLevelService.getLevel(id));
    }

    @Override
    public void addExperience(Long userId, Integer experience, Integer bizType, String bizId) {
        MemberExperienceBizTypeEnum bizTypeEnum = MemberExperienceBizTypeEnum.getByType(bizType);
        if (bizTypeEnum == null) {
            throw exception(EXPERIENCE_BIZ_NOT_SUPPORT);
        }
        memberLevelService.addExperience(userId, experience, bizTypeEnum, bizId);
    }

    @Override
    public void reduceExperience(Long userId, Integer experience, Integer bizType, String bizId) {
        addExperience(userId, -experience, bizType, bizId);
    }

}
