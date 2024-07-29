package cn.econets.blossom.module.system.dal.dataobject.logger;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Operation log table
 *
 */
@TableName(value = "system_operate_log", autoResultMap = true)
@KeySequence("system_operate_log_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class OperateLogDO extends BaseDO {

    /**
     * {@link #javaMethodArgs} Maximum length
     */
    public static final Integer JAVA_METHOD_ARGS_MAX_LENGTH = 8000;

    /**
     * {@link #resultData} Maximum length
     */
    public static final Integer RESULT_MAX_LENGTH = 4000;

    /**
     * Log primary key
     */
    @TableId
    private Long id;
    /**
     * Link tracking number
     *
     * Generally speaking，Tracking number by link，You can access the log，Error log，Link tracking log，logger Print logs, etc.，Combined together，To debug。
     */
    private String traceId;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Properties，Or AdminUserDO of id Properties
     */
    private Long userId;
    /**
     * User Type
     *
     * Relationship {@link  UserTypeEnum}
     */
    private Integer userType;
    /**
     * Operation module
     */
    private String module;
    /**
     * Operation name
     */
    private String name;
    /**
     * Operation Category
     *
     * Enumeration {@link OperateTypeEnum}
     */
    private Integer type;
    /**
     * Operation content，Record the details of the entire operation
     * For example，Change the number to 1 User information，Change gender from male to female。
     */
    private String content;
    /**
     * Extended fields，Some complicated business，Some fields need to be recorded
     * For example，Record order number，You can add key for "orderId"，value Order number
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> exts;

    /**
     * Request method name
     */
    private String requestMethod;
    /**
     * Request address
     */
    private String requestUrl;
    /**
     * User IP
     */
    private String userIp;
    /**
     * Browser UA
     */
    private String userAgent;

    /**
     * Java Method name
     */
    private String javaMethod;
    /**
     * Java Method parameters
     *
     * The actual format is Map<String, Object>
     *     Not used @TableField(typeHandler = FastjsonTypeHandler.class) The reason for the annotation is，Database storage has length limit，Will be cropped，Will lead to JSON Deserialization failed
     *     Among them，key Parameter name，value Parameter value
     */
    private String javaMethodArgs;
    /**
     * Start time
     */
    private LocalDateTime startTime;
    /**
     * Execution duration，Unit：Milliseconds
     */
    private Integer duration;
    /**
     * Result code
     *
     * Currently used {@link CommonResult#getCode()} Properties
     */
    private Integer resultCode;
    /**
     * Result prompt
     *
     * Currently used {@link CommonResult#getMsg()} Properties
     */
    private String resultMsg;
    /**
     * Result data
     *
     * If it is an object，Then use JSON Format
     */
    private String resultData;

}
