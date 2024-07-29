package cn.econets.blossom.module.infrastructure.service.file;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.cache.CacheUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.file.core.client.FileClient;
import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import cn.econets.blossom.framework.file.core.client.FileClientFactory;
import cn.econets.blossom.framework.file.core.enums.FileStorageEnum;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.config.FileConfigPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.config.FileConfigSaveReqVO;
import cn.econets.blossom.module.infrastructure.convert.file.FileConfigConvert;
import cn.econets.blossom.module.infrastructure.dal.mysql.file.FileConfigMapper;
import cn.econets.blossom.module.infrastructure.dal.dataobject.file.FileConfigDO;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.FILE_CONFIG_DELETE_FAIL_MASTER;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.FILE_CONFIG_NOT_EXISTS;

/**
 * File Configuration Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class FileConfigServiceImpl implements FileConfigService {

    private static final Long CACHE_MASTER_ID = 0L;

    /**
     * {@link FileClient} Cache，Refresh asynchronously through it fileClientFactory
     */
    @Getter
    private final LoadingCache<Long, FileClient> clientCache = CacheUtils.buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<Long, FileClient>() {

                @Override
                public FileClient load(Long id) {
                    FileConfigDO config = Objects.equals(CACHE_MASTER_ID, id) ?
                            fileConfigMapper.selectByMaster() : fileConfigMapper.selectById(id);
                    if (config != null) {
                        fileClientFactory.createOrUpdateFileClient(config.getId(), config.getStorage(), config.getConfig());
                    }
                    return fileClientFactory.getFileClient(null == config ? id : config.getId());
                }

            });

    @Resource
    private FileClientFactory fileClientFactory;

    @Resource
    private FileConfigMapper fileConfigMapper;

    @Resource
    private Validator validator;

    @Override
    public Long createFileConfig(FileConfigSaveReqVO createReqVO) {
        FileConfigDO fileConfig = FileConfigConvert.INSTANCE.convert(createReqVO);
        fileConfig.setConfig(parseClientConfig(createReqVO.getStorage(), createReqVO.getConfig()));
        fileConfig.setMaster(false); // Default non master
        fileConfigMapper.insert(fileConfig);
        return fileConfig.getId();
    }

    @Override
    public void updateFileConfig(FileConfigSaveReqVO updateReqVO) {
        // Check existence
        FileConfigDO config = validateFileConfigExists(updateReqVO.getId());
        // Update
        FileConfigDO updateObj = FileConfigConvert.INSTANCE.convert(updateReqVO);
        updateObj.setConfig(parseClientConfig(config.getStorage(), updateReqVO.getConfig()));
        fileConfigMapper.updateById(updateObj);

        // Clear cache
        clearCache(config.getId(), null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileConfigMaster(Long id) {
        // Check existence
        validateFileConfigExists(id);
        // Update others to non master
        FileConfigDO fileConfigDO = new FileConfigDO();
        fileConfigDO.setMaster(false);
        fileConfigMapper.updateBatch(fileConfigDO);
        // Update
        FileConfigDO masterFileConfigDO = new FileConfigDO();
        masterFileConfigDO.setId(id);
        masterFileConfigDO.setMaster(true);
        fileConfigMapper.updateById(masterFileConfigDO);

        // Clear cache
        clearCache(null, true);
    }

    private FileClientConfig parseClientConfig(Integer storage, Map<String, Object> config) {
        // Get configuration class
        Class<? extends FileClientConfig> configClass = FileStorageEnum.getByStorage(storage)
                .getConfigClass();
        FileClientConfig clientConfig = JsonUtils.parseObject2(JsonUtils.toJsonString(config), configClass);
        // Parameter verification
        ValidationUtils.validate(validator, clientConfig);
        // Set parameters
        return clientConfig;
    }

    @Override
    public void deleteFileConfig(Long id) {
        // Check existence
        FileConfigDO config = validateFileConfigExists(id);
        if (Boolean.TRUE.equals(config.getMaster())) {
            throw exception(FILE_CONFIG_DELETE_FAIL_MASTER);
        }
        // Delete
        fileConfigMapper.deleteById(id);

        // Clear cache
        clearCache(id, null);
    }

    /**
     * Clear the specified file configuration
     *
     * @param id Configuration number
     * @param master Is it the main configuration?
     */
    private void clearCache(Long id, Boolean master) {
        if (id != null) {
            clientCache.invalidate(id);
        }
        if (Boolean.TRUE.equals(master)) {
            clientCache.invalidate(CACHE_MASTER_ID);
        }
    }

    private FileConfigDO validateFileConfigExists(Long id) {
        FileConfigDO config = fileConfigMapper.selectById(id);
        if (config == null) {
            throw exception(FILE_CONFIG_NOT_EXISTS);
        }
        return config;
    }

    @Override
    public FileConfigDO getFileConfig(Long id) {
        return fileConfigMapper.selectById(id);
    }

    @Override
    public PageResult<FileConfigDO> getFileConfigPage(FileConfigPageReqVO pageReqVO) {
        return fileConfigMapper.selectPage(pageReqVO);
    }

    @Override
    public String testFileConfig(Long id) throws Exception {
        // Check existence
        validateFileConfigExists(id);
        // Upload file
        byte[] content = ResourceUtil.readBytes("file/erweima.jpg");
        return getFileClient(id).upload(content, IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
    }

    @Override
    public FileClient getFileClient(Long id) {
        return clientCache.getUnchecked(id);
    }

    @Override
    public FileClient getMasterFileClient() {
        return clientCache.getUnchecked(CACHE_MASTER_ID);
    }

}
