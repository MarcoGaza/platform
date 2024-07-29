package cn.econets.blossom.module.system.controller.admin.mail.vo.log;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Management Backend - Mail log Response VO")
@Data
public class MailLogRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31020")
    private Long id;

    @Schema(description = "User Number", example = "30883")
    private Long userId;

    @Schema(description = "User Type，See UserTypeEnum Enumeration", example = "2")
    private Byte userType;

    @Schema(description = "Receiving email address", requiredMode = Schema.RequiredMode.REQUIRED, example = "76854@qq.com")
    private String toMail;

    @Schema(description = "Email account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "18107")
    private Long accountId;

    @Schema(description = "Send email address", requiredMode = Schema.RequiredMode.REQUIRED, example = "85757@qq.com")
    private String fromMail;

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "5678")
    private Long templateId;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    private String templateCode;

    @Schema(description = "Template sender name", example = "Li Si")
    private String templateNickname;

    @Schema(description = "Email Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test title")
    private String templateTitle;

    @Schema(description = "Email content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test content")
    private String templateContent;

    @Schema(description = "Mail parameters", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> templateParams;

    @Schema(description = "Send status，See MailSendStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Byte sendStatus;

    @Schema(description = "Send time")
    private LocalDateTime sendTime;

    @Schema(description = "Send return message ID", example = "28568")
    private String sendMessageId;

    @Schema(description = "Sending exception")
    private String sendException;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
