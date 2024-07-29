package cn.econets.blossom.module.infrastructure.dal.dataobject.logger;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.infrastructure.enums.logger.ApiErrorLogProcessStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * API Abnormal data
 *
 */
@TableName("infra_api_error_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@KeySequence(value = "infra_api_error_log_seq")
public class ApiErrorLogDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * User Number
     */
    private Long userId;
    /**
     * Link tracking number
     *
     * Generally speaking，Tracking number by link，You can access the log，Error log，Link tracking log，logger Print logs, etc.，Combined together，To debug。
     */
    private String traceId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * Application name
     *
     * Currently reading spring.application.name
     */
    private String applicationName;

    // ========== Request related fields ==========

    /**
     * Request method name
     */
    private String requestMethod;
    /**
     * Access address
     */
    private String requestUrl;
    /**
     * Request parameters
     *
     * query: Query String
     * body: Quest Body
     */
    private String requestParams;
    /**
     * User IP
     */
    private String userIp;
    /**
     * Browser UA
     */
    private String userAgent;

    // ========== Exception related fields ==========

    /**
     * Abnormality time
     */
    private LocalDateTime exceptionTime;
    /**
     * Exception name
     *
     * {@link Throwable#getClass()} Full class name
     */
    private String exceptionName;
    /**
     * Message caused by exception
     *
     * {@link cn.hutool.core.exceptions.ExceptionUtil#getMessage(Throwable)}
     */
    private String exceptionMessage;
    /**
     * Root message caused by exception
     *
     * {@link cn.hutool.core.exceptions.ExceptionUtil#getRootCauseMessage(Throwable)}
     */
    private String exceptionRootCauseMessage;
    /**
     * Exception stack trace
     *
     * {@link org.apache.commons.lang3.exception.ExceptionUtils#getStackTrace(Throwable)}
     */
    private String exceptionStackTrace;
    /**
     * The full name of the class where the exception occurred
     *
     * {@link StackTraceElement#getClassName()}
     */
    private String exceptionClassName;
    /**
     * Class file where the exception occurred
     *
     * {@link StackTraceElement#getFileName()}
     */
    private String exceptionFileName;
    /**
     * The name of the method where the exception occurred
     *
     * {@link StackTraceElement#getMethodName()}
     */
    private String exceptionMethodName;
    /**
     * The line where the exception occurred
     *
     * {@link StackTraceElement#getLineNumber()}
     */
    private Integer exceptionLineNumber;

    // ========== Processing related fields ==========

    /**
     * Processing status
     *
     * Enumeration {@link ApiErrorLogProcessStatusEnum}
     */
    private Integer processStatus;
    /**
     * Processing time
     */
    private LocalDateTime processTime;
    /**
     * Processing user number
     *
     * Relationship SysUserDO.SysUserDO#getId()
     */
    private Long processUserId;

}
