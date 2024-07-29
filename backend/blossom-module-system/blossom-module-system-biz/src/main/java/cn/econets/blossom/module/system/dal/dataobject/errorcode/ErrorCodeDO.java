package cn.econets.blossom.module.system.dal.dataobject.errorcode;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.errorcode.ErrorCodeTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Error code table
 *
 */
@TableName(value = "system_error_code")
@KeySequence("system_error_code_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErrorCodeDO extends BaseDO {

    /**
     * Error code number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * Error code type
     *
     * Enumeration {@link ErrorCodeTypeEnum}
     */
    private Integer type;
    /**
     * Application name
     */
    private String applicationName;
    /**
     * Error code
     */
    private Integer code;
    /**
     * Error code error prompt
     */
    private String message;
    /**
     * Error code notes
     */
    private String memo;

}
