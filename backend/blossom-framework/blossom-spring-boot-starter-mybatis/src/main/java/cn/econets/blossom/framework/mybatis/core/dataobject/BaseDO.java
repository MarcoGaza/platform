package cn.econets.blossom.framework.mybatis.core.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Basic entity object
 *
 */
@Data
public abstract class BaseDO implements Serializable {

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * Last updated time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * Creator，Currently in use SysUser of id Number
     *
     * Use String The reason for the type is，There may be non-numeric values ​​in the future，Keep good expandability。
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String creator;
    /**
     * Updater，Currently in use SysUser of id Number
     *
     * Use String The reason for the type is，There may be non-numeric values ​​in the future，Keep good expandability。
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    private String updater;
    /**
     * Delete
     */
    @TableLogic
    private Boolean deleted;

}
