package cn.econets.blossom.framework.mybatis.config;

import cn.econets.blossom.framework.common.util.collection.SetUtils;
import cn.econets.blossom.framework.mybatis.core.enums.SqlConstants;
import cn.econets.blossom.framework.mybatis.core.util.JdbcUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Set;

/**
 * When IdType for {@link IdType#NONE} Time，According to PRIMARY Database used by the data source，Automatically set
 *
 */
@Slf4j
public class IdTypeEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String ID_TYPE_KEY = "mybatis-plus.global-config.db-config.id-type";

    private static final String DATASOURCE_DYNAMIC_KEY = "spring.datasource.dynamic";

    private static final String QUARTZ_JOB_STORE_DRIVER_KEY = "spring.quartz.properties.org.quartz.jobStore.driverDelegateClass";

    private static final Set<DbType> INPUT_ID_TYPES = SetUtils.asSet(DbType.ORACLE, DbType.ORACLE_12C,
            DbType.POSTGRE_SQL, DbType.KINGBASE_ES, DbType.DB2, DbType.H2);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // If it cannot be obtained DbType，No processing will be performed
        DbType dbType = getDbType(environment);
        if (dbType == null) {
            return;
        }

        // Settings Quartz JobStore Corresponding Driver
        setJobStoreDriverIfPresent(environment, dbType);

        // Initialization SQL Static variable
        SqlConstants.init(dbType);

        // If not NONE，No processing will be performed
        IdType idType = getIdType(environment);
        if (idType != IdType.NONE) {
            return;
        }
        // Situation 1，User input ID，Suitable for Oracle、PostgreSQL、Kingbase、DB2、H2 Database
        if (INPUT_ID_TYPES.contains(dbType)) {
            setIdType(environment, IdType.INPUT);
            return;
        }
        // Case 2，Self-increment ID，Suitable for MySQL Databases that are directly incremented
        setIdType(environment, IdType.AUTO);
    }

    public IdType getIdType(ConfigurableEnvironment environment) {
        return environment.getProperty(ID_TYPE_KEY, IdType.class);
    }

    public void setIdType(ConfigurableEnvironment environment, IdType idType) {
        environment.getSystemProperties().put(ID_TYPE_KEY, idType);
        log.info("[setIdType][Modify MyBatis Plus of idType for({})]", idType);
    }

    public void setJobStoreDriverIfPresent(ConfigurableEnvironment environment, DbType dbType) {
        String driverClass = environment.getProperty(QUARTZ_JOB_STORE_DRIVER_KEY);
        if (StrUtil.isNotEmpty(driverClass)) {
            return;
        }
        // According to dbType Type，Get the corresponding driverClass
        switch (dbType) {
            case POSTGRE_SQL:
                driverClass = "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate";
                break;
            case ORACLE:
            case ORACLE_12C:
                driverClass = "org.quartz.impl.jdbcjobstore.oracle.OracleDelegate";
                break;
            case SQL_SERVER:
            case SQL_SERVER2005:
                driverClass = "org.quartz.impl.jdbcjobstore.MSSQLDelegate";
                break;
        }
        // Settings driverClass Variable
        if (StrUtil.isNotEmpty(driverClass)) {
            environment.getSystemProperties().put(QUARTZ_JOB_STORE_DRIVER_KEY, driverClass);
        }
    }

    public static DbType getDbType(ConfigurableEnvironment environment) {
        String primary = environment.getProperty(DATASOURCE_DYNAMIC_KEY + "." + "primary");
        if (StrUtil.isEmpty(primary)) {
            return null;
        }
        String url = environment.getProperty(DATASOURCE_DYNAMIC_KEY + ".datasource." + primary + ".url");
        if (StrUtil.isEmpty(url)) {
            return null;
        }
        return JdbcUtils.getDbType(url);
    }

}
