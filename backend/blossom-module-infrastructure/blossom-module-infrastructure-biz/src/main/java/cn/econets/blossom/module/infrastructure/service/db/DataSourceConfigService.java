package cn.econets.blossom.module.infrastructure.service.db;

import cn.econets.blossom.module.infrastructure.controller.admin.db.vo.DataSourceConfigSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.db.DataSourceConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Data source configuration Service Interface
 *
 *
 */
public interface DataSourceConfigService {

    /**
     * Create data source configuration
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDataSourceConfig(@Valid DataSourceConfigSaveReqVO createReqVO);

    /**
     * Update data source configuration
     *
     * @param updateReqVO Update information
     */
    void updateDataSourceConfig(@Valid DataSourceConfigSaveReqVO updateReqVO);

    /**
     * Delete data source configuration
     *
     * @param id Number
     */
    void deleteDataSourceConfig(Long id);

    /**
     * Get data source configuration
     *
     * @param id Number
     * @return Data source configuration
     */
    DataSourceConfigDO getDataSourceConfig(Long id);

    /**
     * Get the data source configuration list
     *
     * @return Data source configuration list
     */
    List<DataSourceConfigDO> getDataSourceConfigList();

}
