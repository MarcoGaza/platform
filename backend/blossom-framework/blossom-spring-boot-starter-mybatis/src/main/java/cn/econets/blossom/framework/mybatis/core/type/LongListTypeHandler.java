package cn.econets.blossom.framework.mybatis.core.type;

import cn.econets.blossom.framework.common.util.string.StrUtils;
import cn.hutool.core.collection.CollUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * List<Long> Type converter implementation class，Corresponding to the database varchar Type
 *
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class LongListTypeHandler implements TypeHandler<List<Long>> {

    private static final String COMMA = ",";

    @Override
    public void setParameter(PreparedStatement ps, int i, List<Long> strings, JdbcType jdbcType) throws SQLException {
        // Set placeholder
        ps.setString(i, CollUtil.join(strings, COMMA));
    }

    @Override
    public List<Long> getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return getResult(value);
    }

    @Override
    public List<Long> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return getResult(value);
    }

    @Override
    public List<Long> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return getResult(value);
    }

    private List<Long> getResult(String value) {
        if (value == null) {
            return null;
        }
        return StrUtils.splitToLong(value, COMMA);
    }
}
