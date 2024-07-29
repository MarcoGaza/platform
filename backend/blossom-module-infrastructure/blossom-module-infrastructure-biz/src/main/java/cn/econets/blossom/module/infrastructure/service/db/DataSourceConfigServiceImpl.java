package cn.econets.blossom.module.infrastructure.service.db;

import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.mybatis.core.util.JdbcUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.db.vo.DataSourceConfigSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.db.DataSourceConfigDO;
import cn.econets.blossom.module.infrastructure.dal.mysql.db.DataSourceConfigMapper;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_EXISTS;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_OK;

/**
 * Data source configuration Service Implementation class
 *
 *
 */
@Service
@Validated
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    @Resource
    private DataSourceConfigMapper dataSourceConfigMapper;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Override
    public Long createDataSourceConfig(DataSourceConfigSaveReqVO createReqVO) {
        DataSourceConfigDO config = BeanUtils.toBean(createReqVO, DataSourceConfigDO.class);
        validateConnectionOK(config);

        // Insert
        dataSourceConfigMapper.insert(config);
        // Return
        return config.getId();
    }

    @Override
    public void updateDataSourceConfig(DataSourceConfigSaveReqVO updateReqVO) {
        // Check existence
        validateDataSourceConfigExists(updateReqVO.getId());
        DataSourceConfigDO updateObj = BeanUtils.toBean(updateReqVO, DataSourceConfigDO.class);
        validateConnectionOK(updateObj);

        // Update
        dataSourceConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataSourceConfig(Long id) {
        // Check existence
        validateDataSourceConfigExists(id);
        // Delete
        dataSourceConfigMapper.deleteById(id);
    }

    private void validateDataSourceConfigExists(Long id) {
        if (dataSourceConfigMapper.selectById(id) == null) {
            throw exception(DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public DataSourceConfigDO getDataSourceConfig(Long id) {
        // If id for 0，Default is master Data source
        if (Objects.equals(id, DataSourceConfigDO.ID_MASTER)) {
            return buildMasterDataSourceConfig();
        }
        // From DB Reading
        return dataSourceConfigMapper.selectById(id);
    }

    @Override
    public List<DataSourceConfigDO> getDataSourceConfigList() {
        List<DataSourceConfigDO> result = dataSourceConfigMapper.selectList();
        // Supplement master Data Source
        result.add(0, buildMasterDataSourceConfig());
        return result;
    }

    private void validateConnectionOK(DataSourceConfigDO config) {
        boolean success = JdbcUtils.isConnectionOK(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw exception(DATA_SOURCE_CONFIG_NOT_OK);
        }
    }

    private DataSourceConfigDO buildMasterDataSourceConfig() {
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        return new DataSourceConfigDO().setId(DataSourceConfigDO.ID_MASTER).setName(primary)
                .setUrl(dataSourceProperty.getUrl())
                .setUsername(dataSourceProperty.getUsername())
                .setPassword(dataSourceProperty.getPassword());
    }

}
