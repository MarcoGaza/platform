package cn.econets.blossom.module.member.service.signin;

import cn.econets.blossom.module.member.controller.admin.signin.vo.config.MemberSignInConfigCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.signin.vo.config.MemberSignInConfigUpdateReqVO;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Sign-in rules Service Interface
 *
 * 
 */
public interface MemberSignInConfigService {

    /**
     * Create sign-in rules
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSignInConfig(@Valid MemberSignInConfigCreateReqVO createReqVO);

    /**
     * Update sign-in rules
     *
     * @param updateReqVO Update information
     */
    void updateSignInConfig(@Valid MemberSignInConfigUpdateReqVO updateReqVO);

    /**
     * Delete sign-in rules
     *
     * @param id Number
     */
    void deleteSignInConfig(Long id);

    /**
     * Get sign-in rules
     *
     * @param id Number
     * @return Sign-in rules
     */
    MemberSignInConfigDO getSignInConfig(Long id);

    /**
     * Get the sign-in rules list
     *
     * @return Sign-in rules page
     */
    List<MemberSignInConfigDO> getSignInConfigList();

    /**
     * Get the sign-in rules list
     *
     * @param status Status
     * @return Sign-in rules page
     */
    List<MemberSignInConfigDO> getSignInConfigList(Integer status);

}
