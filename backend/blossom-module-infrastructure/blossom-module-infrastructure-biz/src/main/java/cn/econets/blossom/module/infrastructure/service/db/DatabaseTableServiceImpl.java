package cn.econets.blossom.module.infrastructure.service.db;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.infrastructure.dal.dataobject.db.DataSourceConfigDO;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Database table Service Implementation class
 *
 *
 */
@Service
public class DatabaseTableServiceImpl implements DatabaseTableService {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @Override
    public List<TableInfo> getTableList(Long dataSourceConfigId, String nameLike, String commentLike) {
        List<TableInfo> tables = getTableList0(dataSourceConfigId, null);
        return tables.stream().filter(tableInfo -> (StrUtil.isEmpty(nameLike) || tableInfo.getName().contains(nameLike))
                        && (StrUtil.isEmpty(commentLike) || tableInfo.getComment().contains(commentLike)))
                .collect(Collectors.toList());
    }

    @Override
    public TableInfo getTable(Long dataSourceConfigId, String name) {
        return CollUtil.getFirst(getTableList0(dataSourceConfigId, name));
    }

    private List<TableInfo> getTableList0(Long dataSourceConfigId, String name) {
        // Get data source configuration
        DataSourceConfigDO config = dataSourceConfigService.getDataSourceConfig(dataSourceConfigId);
        Assert.notNull(config, "Data Source({}) Does not exist！", dataSourceConfigId);

        // Use MyBatis Plus Generator Analysis table structure
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(config.getUrl(), config.getUsername(),
                config.getPassword()).build();
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        if (StrUtil.isNotEmpty(name)) {
            strategyConfig.addInclude(name);
        } else {
            // Remove the table name prefix of workflow and scheduled tasks // TODO Make it configurable in the future
            strategyConfig.addExclude("ACT_[\\S\\s]+|QRTZ_[\\S\\s]+|FLW_[\\S\\s]+");
        }

        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build(); // Use only LocalDateTime Type，Not used LocalDate
        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfig, strategyConfig.build(),
                null, globalConfig, null);
        // Sort by name
        List<TableInfo> tables = builder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return tables;
    }

}
