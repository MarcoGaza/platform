package cn.econets.blossom.module.infrastructure.dal.dataobject.db;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.EncryptTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Data source configuration
 */
@TableName(value = "infra_data_source_config", autoResultMap = true)
@KeySequence("infra_data_source_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class DataSourceConfigDO extends BaseDO {

    /**
     * Primary key number - Master Data Source
     */
    public static final Long ID_MASTER = 0L;

    /**
     * Primary key number
     */
    private Long id;
    /**
     * Connection name
     */
    private String name;

    /**
     * Data source connection
     */
    private String url;
    /**
     * Username
     */
    private String username;
    /**
     * Password
     */
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String password;

}
