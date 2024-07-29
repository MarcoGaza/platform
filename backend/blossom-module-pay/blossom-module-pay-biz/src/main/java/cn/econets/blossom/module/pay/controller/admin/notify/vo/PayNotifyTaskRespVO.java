package cn.econets.blossom.module.pay.controller.admin.notify.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Callback notification Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayNotifyTaskRespVO extends PayNotifyTaskBaseVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "3380")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Application Name", example = "wx_pay")
    private String  appName;

}
