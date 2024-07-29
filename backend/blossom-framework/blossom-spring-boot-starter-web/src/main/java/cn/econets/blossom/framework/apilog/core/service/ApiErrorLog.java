package cn.econets.blossom.framework.apilog.core.service;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * API Error log
 *
 */
@Data
public class ApiErrorLog {

    /**
     * Link number
     */
    private String traceId;
    /**
     * Account Number
     */
    private Long userId;
    /**
     * User Type
     */
    private Integer userType;
    /**
     * Application Name
     */
    @NotNull(message = "Application name cannot be empty")
    private String applicationName;

    /**
     * Request method name
     */
    @NotNull(message = "http The request method cannot be empty")
    private String requestMethod;
    /**
     * Access address
     */
    @NotNull(message = "The access address cannot be empty")
    private String requestUrl;
    /**
     * Request parameters
     */
    @NotNull(message = "Request parameters cannot be empty")
    private String requestParams;
    /**
     * User IP
     */
    @NotNull(message = "ip Cannot be empty")
    private String userIp;
    /**
     * Browser UA
     */
    @NotNull(message = "User-Agent Cannot be empty")
    private String userAgent;

    /**
     * Abnormal time
     */
    @NotNull(message = "Exception time cannot be empty")
    private LocalDateTime exceptionTime;
    /**
     * Exception name
     */
    @NotNull(message = "Exception name cannot be empty")
    private String exceptionName;
    /**
     * The full name of the class where the exception occurred
     */
    @NotNull(message = "The full name of the class where the exception occurred cannot be empty")
    private String exceptionClassName;
    /**
     * Class file where the exception occurred
     */
    @NotNull(message = "The class file where the exception occurred cannot be empty")
    private String exceptionFileName;
    /**
     * The method name where the exception occurred
     */
    @NotNull(message = "The method name where the exception occurred cannot be empty")
    private String exceptionMethodName;
    /**
     * The line where the exception occurred
     */
    @NotNull(message = "The line where the method where the exception occurs cannot be empty")
    private Integer exceptionLineNumber;
    /**
     * Exception stack traceException stack trace
     */
    @NotNull(message = "Exception stack trace cannot be empty")
    private String exceptionStackTrace;
    /**
     * Root message caused by exception
     */
    @NotNull(message = "The root message caused by the exception cannot be empty")
    private String exceptionRootCauseMessage;
    /**
     * Message caused by exception
     */
    @NotNull(message = "The message caused by the exception cannot be empty")
    private String exceptionMessage;


}
