package cn.econets.blossom.module.system.controller.admin.notify.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Management Backend - Internal message Response VO")
@Data
public class NotifyMessageRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "25025")
    private Long userId;

    @Schema(description = "User Type，See UserTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Byte userType;

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13013")
    private Long templateId;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    private String templateCode;

    @Schema(description = "Template sender name", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String templateNickname;

    @Schema(description = "Template content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test content")
    private String templateContent;

    @Schema(description = "Template type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer templateType;

    @Schema(description = "Template parameters", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> templateParams;

    @Schema(description = "Has it been read?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean readStatus;

    @Schema(description = "Reading time")
    private LocalDateTime readTime;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
