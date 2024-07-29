package cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apierrorlog;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - API Error log Response VO")
@Data
@ExcelIgnoreUnannotated
public class ApiErrorLogRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Number")
    private Integer id;

    @Schema(description = "Link tracking number", requiredMode = Schema.RequiredMode.REQUIRED, example = "66600cb6-7852-11eb-9439-0242ac130002")
    @ExcelProperty("Link tracking number")
    private String traceId;

    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    @ExcelProperty("User ID")
    private Integer userId;

    @Schema(description = "User Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "User Type", converter = DictConvert.class)
    @DictFormat(cn.econets.blossom.module.system.enums.DictTypeConstants.USER_TYPE)
    private Integer userType;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "dashboard")
    @ExcelProperty("Application Name")
    private String applicationName;

    @Schema(description = "Request method name", requiredMode = Schema.RequiredMode.REQUIRED, example = "GET")
    @ExcelProperty("Request method name")
    private String requestMethod;

    @Schema(description = "Request address", requiredMode = Schema.RequiredMode.REQUIRED, example = "/xx/yy")
    @ExcelProperty("Request address")
    private String requestUrl;

    @Schema(description = "Request parameters", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Request parameters")
    private String requestParams;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @ExcelProperty("User IP")
    private String userIp;

    @Schema(description = "Browser UA", requiredMode = Schema.RequiredMode.REQUIRED, example = "Mozilla/5.0")
    @ExcelProperty("Browser UA")
    private String userAgent;

    @Schema(description = "Abnormality time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Abnormality occurred time")
    private LocalDateTime exceptionTime;

    @Schema(description = "Exception name", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Exception name")
    private String exceptionName;

    @Schema(description = "Message caused by exception", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Message caused by exception")
    private String exceptionMessage;

    @Schema(description = "Root message caused by exception", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Root message caused by exception")
    private String exceptionRootCauseMessage;

    @Schema(description = "Exception stack trace", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Exception stack trace")
    private String exceptionStackTrace;

    @Schema(description = "The full name of the class where the exception occurred", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("The full name of the class where the exception occurred")
    private String exceptionClassName;

    @Schema(description = "Class file where the exception occurred", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Class file where the exception occurred")
    private String exceptionFileName;

    @Schema(description = "The name of the method where the exception occurred", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("The name of the method where the exception occurred")
    private String exceptionMethodName;

    @Schema(description = "The line where the exception occurred", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("The line where the exception occurred")
    private Integer exceptionLineNumber;

    @Schema(description = "Processing status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty(value = "Processing status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.API_ERROR_LOG_PROCESS_STATUS)
    private Integer processStatus;

    @Schema(description = "Processing time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Processing time")
    private LocalDateTime processTime;

    @Schema(description = "Processing user number", example = "233")
    @ExcelProperty("Processing user ID")
    private Integer processUserId;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
