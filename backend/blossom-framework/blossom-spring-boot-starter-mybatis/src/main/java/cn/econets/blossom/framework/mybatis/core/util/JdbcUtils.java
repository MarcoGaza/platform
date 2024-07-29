package cn.econets.blossom.framework.mybatis.core.util;

import com.baomidou.mybatisplus.annotation.DbType;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC Tools
 *
 */
public class JdbcUtils {

    /**
     * Judge whether the connection is correct
     *
     * @param url      Data source connection
     * @param username Account
     * @param password Password
     * @return Is it correct?
     */
    public static boolean isConnectionOK(String url, String username, String password) {
        try (Connection ignored = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Get URL Corresponding DB Type
     *
     * @param url URL
     * @return DB Type
     */
    public static DbType getDbType(String url) {
        String name = com.alibaba.druid.util.JdbcUtils.getDbType(url, null);
        return DbType.getDbType(name);
    }

}
