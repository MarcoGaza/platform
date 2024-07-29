package cn.econets.blossom.module.system.controller.admin.sms.vo.log;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - SMS log paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmsLogPageReqVO extends PageParam {

    @Schema(description = "SMS channel number", example = "10")
    private Long channelId;

    @Schema(description = "Template number", example = "20")
    private Long templateId;

    @Schema(description = "Mobile phone number", example = "15601691300")
    private String mobile;

    @Schema(description = "Send status，See SmsSendStatusEnum Enumeration class", example = "1")
    private Integer sendStatus;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Send time")
    private LocalDateTime[] sendTime;

    @Schema(description = "Receiving status，See SmsReceiveStatusEnum Enumeration class", example = "0")
    private Integer receiveStatus;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Receive time")
    private LocalDateTime[] receiveTime;

}
