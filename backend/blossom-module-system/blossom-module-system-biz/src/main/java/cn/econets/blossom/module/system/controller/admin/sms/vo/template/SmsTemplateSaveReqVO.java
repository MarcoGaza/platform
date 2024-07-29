package cn.econets.blossom.module.system.controller.admin.sms.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Create SMS template/Modify Request VO")
@Data
public class SmsTemplateSaveReqVO {

    @Schema(description = "Number", example = "1024")
    private Long id;

    @Schema(description = "SMS type，See SmsTemplateTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The SMS type cannot be empty")
    private Integer type;

    @Schema(description = "Open status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The open state cannot be empty")
    private Integer status;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @NotNull(message = "Template code cannot be empty")
    private String code;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotNull(message = "Template name cannot be empty")
    private String name;

    @Schema(description = "Template content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hello，{name}。You are too long{like}La！")
    @NotNull(message = "Template content cannot be empty")
    private String content;

    @Schema(description = "Remarks", example = "Hahaha")
    private String remark;

    @Schema(description = "SMS API Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4383920")
    @NotNull(message = "SMS API The template number cannot be empty")
    private String apiTemplateId;

    @Schema(description = "SMS channel number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "The SMS channel number cannot be empty")
    private Long channelId;

}
