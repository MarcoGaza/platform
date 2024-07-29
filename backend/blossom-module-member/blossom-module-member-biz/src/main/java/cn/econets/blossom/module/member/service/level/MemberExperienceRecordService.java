package cn.econets.blossom.module.member.service.level;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.level.vo.experience.MemberExperienceRecordPageReqVO;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberExperienceRecordDO;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;

/**
 * Member Experience Record Service Interface
 *
 *
 */
public interface MemberExperienceRecordService {

    /**
     * Get member experience record
     *
     * @param id Number
     * @return Member Experience Record
     */
    MemberExperienceRecordDO getExperienceRecord(Long id);

    /**
     * 【Administrator】Get member experience record page
     *
     * @param pageReqVO Paged query
     * @return Member Experience Record Paging
     */
    PageResult<MemberExperienceRecordDO> getExperienceRecordPage(MemberExperienceRecordPageReqVO pageReqVO);

    /**
     * 【Member】Get member experience record page
     *
     * @param userId User Number
     * @param pageParam Paged query
     * @return Member Experience Record Paging
     */
    PageResult<MemberExperienceRecordDO> getExperienceRecordPage(Long userId, PageParam pageParam);

    /**
     * Based on business type, Create Experience change record
     *
     * @param userId          Member Number
     * @param experience      Change Experience Value
     * @param totalExperience Member's current experience
     * @param bizType         Business Type
     * @param bizId           BusinessID
     */
    void createExperienceRecord(Long userId, Integer experience, Integer totalExperience,
                                MemberExperienceBizTypeEnum bizType, String bizId);

}
