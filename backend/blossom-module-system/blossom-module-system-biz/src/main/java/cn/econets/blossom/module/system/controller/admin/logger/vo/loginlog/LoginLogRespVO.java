package cn.econets.blossom.module.system.controller.admin.logger.vo.loginlog;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Login log Response VO")
@Data
@ExcelIgnoreUnannotated
public class LoginLogRespVO {

    @Schema(description = "Log number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Log primary key")
    private Long id;

    @Schema(description = "Log type，See LoginLogTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Log type", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.LOGIN_TYPE)
    private Integer logType;

    @Schema(description = "User Number", example = "666")
    private Long userId;

    @Schema(description = "User Type，See UserTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer userType;

    @Schema(description = "Link tracking number", example = "89aca178-a370-411c-ae02-3f0d672be4ab")
    private String traceId;

    @Schema(description = "User Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @ExcelProperty("User Account")
    private String username;

    @Schema(description = "Login result，See LoginResultEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Login result", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.LOGIN_RESULT)
    private Integer result;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @ExcelProperty("Login IP")
    private String userIp;

    @Schema(description = "Browser UserAgent", example = "Mozilla/5.0")
    @ExcelProperty("Browser UA")
    private String userAgent;

    @Schema(description = "Login time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Login time")
    private LocalDateTime createTime;

}
