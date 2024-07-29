package cn.econets.blossom.module.member.api.config;

import cn.econets.blossom.module.member.api.config.dto.MemberConfigRespDTO;

/**
 * User Configuration API Interface
 *
 * 
 */
public interface MemberConfigApi {

    /**
     * Get points configuration
     *
     * @return Points Configuration
     */
    MemberConfigRespDTO getConfig();
}
