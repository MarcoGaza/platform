package cn.econets.blossom.module.system.controller.admin.logger.vo.operatelog;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Management Backend - Operation log Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperateLogRespVO {

    @Schema(description = "Log number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Log number")
    private Long id;

    @Schema(description = "Link tracking number", requiredMode = Schema.RequiredMode.REQUIRED, example = "89aca178-a370-411c-ae02-3f0d672be4ab")
    private String traceId;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @ExcelProperty("Operator")
    private String userNickname;

    @Schema(description = "Operation module", requiredMode = Schema.RequiredMode.REQUIRED, example = "Order")
    @ExcelProperty("Operation module")
    private String module;

    @Schema(description = "Operation name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Create order")
    @ExcelProperty("Operation name")
    private String name;

    @Schema(description = "Operation Category，See OperateLogTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Operation type", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.OPERATE_TYPE)
    private Integer type;

    @Schema(description = "Operation details", example = "Change the number to 1 User information，Change gender from male to female。")
    private String content;

    @Schema(description = "Extended fields", example = "{'orderId': 1}")
    private Map<String, Object> exts;

    @Schema(description = "Request method name", requiredMode = Schema.RequiredMode.REQUIRED, example = "GET")
    @NotEmpty(message = "The request method name cannot be empty")
    private String requestMethod;

    @Schema(description = "Request address", requiredMode = Schema.RequiredMode.REQUIRED, example = "/xxx/yyy")
    private String requestUrl;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    private String userIp;

    @Schema(description = "Browser UserAgent", requiredMode = Schema.RequiredMode.REQUIRED, example = "Mozilla/5.0")
    private String userAgent;

    @Schema(description = "Java Method name", requiredMode = Schema.RequiredMode.REQUIRED, example = "cn.econets.blossom.adminserver.UserController.save(...)")
    private String javaMethod;

    @Schema(description = "Java Method parameters")
    private String javaMethodArgs;

    @Schema(description = "Start time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Operation log")
    private LocalDateTime startTime;

    @Schema(description = "Execution duration，Unit：Milliseconds", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Execution duration")
    private Integer duration;

    @Schema(description = "Result code", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "Result code")
    private Integer resultCode;

    @Schema(description = "Result prompt")
    private String resultMsg;

    @Schema(description = "Result data")
    private String resultData;

}
