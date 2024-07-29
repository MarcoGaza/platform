package cn.econets.blossom.module.infrastructure.service.config;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.controller.admin.config.vo.ConfigPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.config.vo.ConfigSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.config.ConfigDO;

import javax.validation.Valid;

/**
 * Parameter configuration Service Interface
 *
 *
 */
public interface ConfigService {

    /**
     * Create parameter configuration
     *
     * @param createReqVO Create information
     * @return Configuration number
     */
    Long createConfig(@Valid ConfigSaveReqVO createReqVO);

    /**
     * Update parameter configuration
     *
     * @param updateReqVO Update information
     */
    void updateConfig(@Valid ConfigSaveReqVO updateReqVO);

    /**
     * Delete parameter configuration
     *
     * @param id Configuration number
     */
    void deleteConfig(Long id);

    /**
     * Get parameter configuration
     *
     * @param id Configuration number
     * @return Parameter configuration
     */
    ConfigDO getConfig(Long id);

    /**
     * According to parameter key，Get parameter configuration
     *
     * @param key Configuration key
     * @return Parameter configuration
     */
    ConfigDO getConfigByKey(String key);

    /**
     * Get parameter configuration paging list
     *
     * @param reqVO Pagination conditions
     * @return Paged list
     */
    PageResult<ConfigDO> getConfigPage(@Valid ConfigPageReqVO reqVO);

}
