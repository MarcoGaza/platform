package cn.econets.blossom.module.system.controller.admin.sms.vo.log;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.framework.excel.core.convert.JsonConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Management Backend - SMS log Response VO")
@Data
@ExcelIgnoreUnannotated
public class SmsLogRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Number")
    private Long id;

    @Schema(description = "SMS channel number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @ExcelProperty("SMS channel number")
    private Long channelId;

    @Schema(description = "SMS channel code", requiredMode = Schema.RequiredMode.REQUIRED, example = "ALIYUN")
    @ExcelProperty("SMS channel code")
    private String channelCode;

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    @ExcelProperty("Template number")
    private Long templateId;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "test-01")
    @ExcelProperty("Template encoding")
    private String templateCode;

    @Schema(description = "SMS type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "SMS type", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_TEMPLATE_TYPE)
    private Integer templateType;

    @Schema(description = "SMS content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hello，Your verification code is 1024")
    @ExcelProperty("SMS content")
    private String templateContent;

    @Schema(description = "SMS parameters", requiredMode = Schema.RequiredMode.REQUIRED, example = "name,code")
    @ExcelProperty(value = "SMS parameters", converter = JsonConvert.class)
    private Map<String, Object> templateParams;

    @Schema(description = "SMS API Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "SMS_207945135")
    @ExcelProperty("SMS API Template number")
    private String apiTemplateId;

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
    @ExcelProperty("Mobile phone number")
    private String mobile;

    @Schema(description = "User Number", example = "10")
    @ExcelProperty("User Number")
    private Long userId;

    @Schema(description = "User Type", example = "1")
    @ExcelProperty(value = "User Type", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.USER_TYPE)
    private Integer userType;

    @Schema(description = "Send status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Send status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_SEND_STATUS)
    private Integer sendStatus;

    @Schema(description = "Send time")
    @ExcelProperty("Send time")
    private LocalDateTime sendTime;

    @Schema(description = "SMS API The encoding of the sending result", example = "SUCCESS")
    @ExcelProperty("SMS API The encoding of the sent result")
    private String apiSendCode;

    @Schema(description = "SMS API Send failed prompt", example = "Success")
    @ExcelProperty("SMS API Send failed prompt")
    private String apiSendMsg;

    @Schema(description = "SMS API Send the only request returned ID", example = "3837C6D3-B96F-428C-BBB2-86135D4B5B99")
    @ExcelProperty("SMS API Send the only request returned ID")
    private String apiRequestId;

    @Schema(description = "SMS API Send the returned sequence number", example = "62923244790")
    @ExcelProperty("SMS API Send the returned sequence number")
    private String apiSerialNo;

    @Schema(description = "Receiving status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty(value = "Receiving status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SMS_RECEIVE_STATUS)
    private Integer receiveStatus;

    @Schema(description = "Receive time")
    @ExcelProperty("Receive time")
    private LocalDateTime receiveTime;

    @Schema(description = "API The encoding of the received result", example = "DELIVRD")
    @ExcelProperty("API The encoding of the received result")
    private String apiReceiveCode;

    @Schema(description = "API Description of receiving results", example = "User received successfully")
    @ExcelProperty("API Description of receiving results")
    private String apiReceiveMsg;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
