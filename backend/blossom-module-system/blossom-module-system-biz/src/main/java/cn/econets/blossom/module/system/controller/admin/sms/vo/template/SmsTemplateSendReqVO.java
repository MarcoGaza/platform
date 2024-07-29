package cn.econets.blossom.module.system.controller.admin.sms.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "Management Backend - Sending SMS template Request VO")
@Data
public class SmsTemplateSendReqVO {

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
    @NotNull(message = "Mobile number cannot be empty")
    private String mobile;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @NotNull(message = "Template code cannot be empty")
    private String templateCode;

    @Schema(description = "Template parameters")
    private Map<String, Object> templateParams;

}
