package cn.econets.blossom.module.system.controller.admin.mail.vo.log;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Mailbox log paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailLogPageReqVO extends PageParam {

    @Schema(description = "User Number", example = "30883")
    private Long userId;

    @Schema(description = "User Type，See UserTypeEnum Enumeration", example = "2")
    private Integer userType;

    @Schema(description = "Receiving email address，Fuzzy matching", example = "76854@qq.com")
    private String toMail;

    @Schema(description = "Email account number", example = "18107")
    private Long accountId;

    @Schema(description = "Template number", example = "5678")
    private Long templateId;

    @Schema(description = "Send status，See MailSendStatusEnum Enumeration", example = "1")
    private Integer sendStatus;

    @Schema(description = "Send time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendTime;

}
