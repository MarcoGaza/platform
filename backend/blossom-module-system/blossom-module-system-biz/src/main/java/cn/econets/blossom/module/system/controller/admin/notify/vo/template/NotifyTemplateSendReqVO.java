package cn.econets.blossom.module.system.controller.admin.notify.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "Management Backend - Sending internal message templates Request VO")
@Data
public class NotifyTemplateSendReqVO {

    @Schema(description = "Userid", requiredMode = Schema.RequiredMode.REQUIRED, example = "01")
    @NotNull(message = "UseridCannot be empty")
    private Long userId;

    @Schema(description = "User Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "User type cannot be empty")
    private Integer userType;

    @Schema(description = "Template encoding", requiredMode = Schema.RequiredMode.REQUIRED, example = "01")
    @NotEmpty(message = "Template code cannot be empty")
    private String templateCode;

    @Schema(description = "Template parameters")
    private Map<String, Object> templateParams;

}
