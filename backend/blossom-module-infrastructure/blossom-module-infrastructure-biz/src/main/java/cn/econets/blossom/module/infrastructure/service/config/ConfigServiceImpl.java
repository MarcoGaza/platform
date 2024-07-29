package cn.econets.blossom.module.infrastructure.service.config;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.controller.admin.config.vo.ConfigPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.config.vo.ConfigSaveReqVO;
import cn.econets.blossom.module.infrastructure.convert.config.ConfigConvert;
import cn.econets.blossom.module.infrastructure.dal.dataobject.config.ConfigDO;
import cn.econets.blossom.module.infrastructure.dal.mysql.config.ConfigMapper;
import cn.econets.blossom.module.infrastructure.enums.config.ConfigTypeEnum;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.*;

/**
 * Parameter configuration Service Implementation class
 */
@Service
@Slf4j
@Validated
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Override
    public Long createConfig(ConfigSaveReqVO createReqVO) {
        // Verify parameter configuration key Uniqueness
        validateConfigKeyUnique(null, createReqVO.getKey());

        // Insert parameter configuration
        ConfigDO config = ConfigConvert.INSTANCE.convert(createReqVO);
        config.setType(ConfigTypeEnum.CUSTOM.getType());
        configMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateConfig(ConfigSaveReqVO updateReqVO) {
        // Verify own existence
        validateConfigExists(updateReqVO.getId());
        // Verify parameter configuration key Uniqueness
        validateConfigKeyUnique(updateReqVO.getId(), updateReqVO.getKey());

        // Update parameter configuration
        ConfigDO updateObj = ConfigConvert.INSTANCE.convert(updateReqVO);
        configMapper.updateById(updateObj);
    }

    @Override
    public void deleteConfig(Long id) {
        // Verify configuration exists
        ConfigDO config = validateConfigExists(id);
        // Built-in configuration，Deletion is not allowed
        if (ConfigTypeEnum.SYSTEM.getType().equals(config.getType())) {
            throw exception(CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
        }
        // Delete
        configMapper.deleteById(id);
    }

    @Override
    public ConfigDO getConfig(Long id) {
        return configMapper.selectById(id);
    }

    @Override
    public ConfigDO getConfigByKey(String key) {
        return configMapper.selectByKey(key);
    }

    @Override
    public PageResult<ConfigDO> getConfigPage(ConfigPageReqVO pageReqVO) {
        return configMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    public ConfigDO validateConfigExists(Long id) {
        if (id == null) {
            return null;
        }
        ConfigDO config = configMapper.selectById(id);
        if (config == null) {
            throw exception(CONFIG_NOT_EXISTS);
        }
        return config;
    }

    @VisibleForTesting
    public void validateConfigKeyUnique(Long id, String key) {
        ConfigDO config = configMapper.selectByKey(key);
        if (config == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Parameter configuration
        if (id == null) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
        if (!config.getId().equals(id)) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
    }

}
