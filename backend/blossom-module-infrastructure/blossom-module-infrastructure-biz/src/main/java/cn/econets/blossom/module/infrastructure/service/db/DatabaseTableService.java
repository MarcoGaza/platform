package cn.econets.blossom.module.infrastructure.service.db;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.List;

/**
 * Database table Service
 *
 *
 */
public interface DatabaseTableService {

    /**
     * Get table list，Based on table name + Fuzzy matching of table description
     *
     * @param dataSourceConfigId Data source configuration number
     * @param nameLike Table name，Fuzzy matching
     * @param commentLike Table description，Fuzzy matching
     * @return Table List
     */
    List<TableInfo> getTableList(Long dataSourceConfigId, String nameLike, String commentLike);

    /**
     * Get the specified table name
     *
     * @param dataSourceConfigId Data source configuration number
     * @param tableName Table name
     * @return Table
     */
    TableInfo getTable(Long dataSourceConfigId, String tableName);

}
