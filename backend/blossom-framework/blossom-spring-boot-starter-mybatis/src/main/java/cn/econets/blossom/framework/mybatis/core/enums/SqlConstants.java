package cn.econets.blossom.framework.mybatis.core.enums;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * SQLRelated constant classes
 *
 */
public class SqlConstants {

    /**
     * Database type
     */
    public static DbType DB_TYPE;

    public static void init(DbType dbType) {
        DB_TYPE = dbType;
    }

}
