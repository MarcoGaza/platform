package cn.econets.blossom.module.infrastructure.service.file;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.file.core.client.FileClient;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.config.FileConfigPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.config.FileConfigSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.file.FileConfigDO;

import javax.validation.Valid;

/**
 * File Configuration Service Interface
 *
 */
public interface FileConfigService {

    /**
     * Create file configuration
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createFileConfig(@Valid FileConfigSaveReqVO createReqVO);

    /**
     * Update file configuration
     *
     * @param updateReqVO Update information
     */
    void updateFileConfig(@Valid FileConfigSaveReqVO updateReqVO);

    /**
     * Update file configuration to Master
     *
     * @param id Number
     */
    void updateFileConfigMaster(Long id);

    /**
     * Delete file configuration
     *
     * @param id Number
     */
    void deleteFileConfig(Long id);

    /**
     * Get file configuration
     *
     * @param id Number
     * @return File Configuration
     */
    FileConfigDO getFileConfig(Long id);

    /**
     * Get file configuration page
     *
     * @param pageReqVO Paged query
     * @return File Configuration Paging
     */
    PageResult<FileConfigDO> getFileConfigPage(FileConfigPageReqVO pageReqVO);

    /**
     * Test whether the file configuration is correct，By uploading files
     *
     * @param id Number
     * @return File URL
     */
    String testFileConfig(Long id) throws Exception;

    /**
     * Get the file client with the specified number
     *
     * @param id Configuration number
     * @return File Client
     */
    FileClient getFileClient(Long id);

    /**
     * Get Master File Client
     *
     * @return File Client
     */
    FileClient getMasterFileClient();

}
