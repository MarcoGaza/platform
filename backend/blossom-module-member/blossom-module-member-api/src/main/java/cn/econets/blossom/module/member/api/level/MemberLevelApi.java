package cn.econets.blossom.module.member.api.level;

import cn.econets.blossom.module.member.api.level.dto.MemberLevelRespDTO;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;

/**
 * Member Level API Interface
 *
 * 
 */
public interface MemberLevelApi {

    /**
     * Get membership level
     *
     * @param id Member Level Number
     * @return Member Level
     */
    MemberLevelRespDTO getMemberLevel(Long id);

    /**
     * Increase member experience
     *
     * @param userId     MemberID
     * @param experience Experience
     * @param bizType    Business Type {@link MemberExperienceBizTypeEnum}
     * @param bizId      Business Number
     */
    void addExperience(Long userId, Integer experience, Integer bizType, String bizId);

    /**
     * Deduct member experience
     *
     * @param userId     MemberID
     * @param experience Experience
     * @param bizType    Business Type {@link MemberExperienceBizTypeEnum}
     * @param bizId      Business Number
     */
    void reduceExperience(Long userId, Integer experience, Integer bizType, String bizId);

}
