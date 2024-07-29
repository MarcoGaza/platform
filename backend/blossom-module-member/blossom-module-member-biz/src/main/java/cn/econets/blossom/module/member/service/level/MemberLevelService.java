package cn.econets.blossom.module.member.service.level;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.module.member.controller.admin.level.vo.level.MemberLevelCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.level.vo.level.MemberLevelListReqVO;
import cn.econets.blossom.module.member.controller.admin.level.vo.level.MemberLevelUpdateReqVO;
import cn.econets.blossom.module.member.controller.admin.user.vo.MemberUserUpdateLevelReqVO;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelDO;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Member Level Service Interface
 *
 * 
 */
public interface MemberLevelService {

    /**
     * Create membership level
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createLevel(@Valid MemberLevelCreateReqVO createReqVO);

    /**
     * Update membership level
     *
     * @param updateReqVO Update information
     */
    void updateLevel(@Valid MemberLevelUpdateReqVO updateReqVO);

    /**
     * Delete membership level
     *
     * @param id Number
     */
    void deleteLevel(Long id);

    /**
     * Get membership level
     *
     * @param id Number
     * @return Member Level
     */
    MemberLevelDO getLevel(Long id);

    /**
     * Get member level list
     *
     * @param ids Number
     * @return Member Level List
     */
    List<MemberLevelDO> getLevelList(Collection<Long> ids);

    /**
     * Get member level list
     *
     * @param listReqVO Query parameters
     * @return Member Level List
     */
    List<MemberLevelDO> getLevelList(MemberLevelListReqVO listReqVO);

    /**
     * Get the member level list of the specified status
     *
     * @param status Status
     * @return Member Level List
     */
    List<MemberLevelDO> getLevelListByStatus(Integer status);

    /**
     * Get the list of enabled member levels
     *
     * @return Member Level List
     */
    default List<MemberLevelDO> getEnableLevelList() {
        return getLevelListByStatus(CommonStatusEnum.ENABLE.getStatus());
    }

    /**
     * Modify member level
     *
     * @param updateReqVO Modify parameters
     */
    void updateUserLevel(MemberUserUpdateLevelReqVO updateReqVO);

    /**
     * Increase member experience
     *
     * @param userId     MemberID
     * @param experience Experience
     * @param bizType    Business Type
     * @param bizId      Business Number
     */
    void addExperience(Long userId, Integer experience, MemberExperienceBizTypeEnum bizType, String bizId);

}
