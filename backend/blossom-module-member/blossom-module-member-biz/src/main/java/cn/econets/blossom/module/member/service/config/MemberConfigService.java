package cn.econets.blossom.module.member.service.config;

import cn.econets.blossom.module.member.controller.admin.config.vo.MemberConfigSaveReqVO;
import cn.econets.blossom.module.member.dal.dataobject.config.MemberConfigDO;

import javax.validation.Valid;

/**
 * Member Configuration Service Interface
 *
 * 
 */
public interface MemberConfigService {

    /**
     * Save member configuration
     *
     * @param saveReqVO Update information
     */
    void saveConfig(@Valid MemberConfigSaveReqVO saveReqVO);

    /**
     * Get member configuration
     *
     * @return Points Configuration
     */
    MemberConfigDO getConfig();

}
