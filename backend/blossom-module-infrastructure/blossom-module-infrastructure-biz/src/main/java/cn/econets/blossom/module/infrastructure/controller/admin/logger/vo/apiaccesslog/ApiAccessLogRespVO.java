package cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apiaccesslog;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - API Access log Response VO")
@Data
@ExcelIgnoreUnannotated
public class ApiAccessLogRespVO {

    @Schema(description = "Log primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Log primary key")
    private Long id;

    @Schema(description = "Link tracking number", requiredMode = Schema.RequiredMode.REQUIRED, example = "66600cb6-7852-11eb-9439-0242ac130002")
    @ExcelProperty("Link tracking number")
    private String traceId;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    @ExcelProperty("User Number")
    private Long userId;

    @Schema(description = "User Type，See UserTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "User Type", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_TYPE)
    private Integer userType;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "dashboard")
    @ExcelProperty("Application name")
    private String applicationName;

    @Schema(description = "Request method name", requiredMode = Schema.RequiredMode.REQUIRED, example = "GET")
    @ExcelProperty("Request method name")
    private String requestMethod;

    @Schema(description = "Request address", requiredMode = Schema.RequiredMode.REQUIRED, example = "/xxx/yyy")
    @ExcelProperty("Request address")
    private String requestUrl;

    @Schema(description = "Request parameters")
    @ExcelProperty("Request parameters")
    private String requestParams;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @ExcelProperty("User IP")
    private String userIp;

    @Schema(description = "Browser UA", requiredMode = Schema.RequiredMode.REQUIRED, example = "Mozilla/5.0")
    @ExcelProperty("Browser UA")
    private String userAgent;

    @Schema(description = "Start request time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Start request time")
    private LocalDateTime beginTime;

    @Schema(description = "End request time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("End request time")
    private LocalDateTime endTime;

    @Schema(description = "Execution duration", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @ExcelProperty("Execution duration")
    private Integer duration;

    @Schema(description = "Result code", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty("Result code")
    private Integer resultCode;

    @Schema(description = "Result prompt", example = "blossom，Awesome！")
    @ExcelProperty("Result prompt")
    private String resultMsg;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
