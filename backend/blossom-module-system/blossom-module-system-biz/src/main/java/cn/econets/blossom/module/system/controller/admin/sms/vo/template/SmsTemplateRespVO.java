package cn.econets.blossom.module.system.controller.admin.sms.vo.template;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - SMS template Response VO")
@Data
@ExcelIgnoreUnannotated
public class SmsTemplateRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Number")
    private Long id;

    @Schema(description = "SMS type，See SmsTemplateTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "SMS signature", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_TEMPLATE_TYPE)
    private Integer type;

    @Schema(description = "Open status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Open status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @ExcelProperty("Template encoding")
    private String code;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @ExcelProperty("Template name")
    private String name;

    @Schema(description = "Template content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hello，{name}。You are too long{like}La！")
    @ExcelProperty("Template content")
    private String content;

    @Schema(description = "Parameter array", example = "name,code")
    private List<String> params;

    @Schema(description = "Remarks", example = "Hahaha")
    @ExcelProperty("Remarks")
    private String remark;

    @Schema(description = "SMS API Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4383920")
    @ExcelProperty("SMS API Template number")
    private String apiTemplateId;

    @Schema(description = "SMS channel number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @ExcelProperty("SMS channel number")
    private Long channelId;

    @Schema(description = "SMS channel code", requiredMode = Schema.RequiredMode.REQUIRED, example = "ALIYUN")
    @ExcelProperty(value = "SMS channel code", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_CHANNEL_CODE)
    private String channelCode;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
