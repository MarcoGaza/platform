
package cn.econets.blossom.module.pay.controller.admin.notify.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Callback notification details Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayNotifyTaskDetailRespVO extends PayNotifyTaskBaseVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "3380")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

    @Schema(description = "Application Name", example = "wx_pay")
    private String appName;

    @Schema(description = "Callback log list")
    private List<Log> logs;

    @Schema(description = "Management Backend - Callback log")
    @Data
    public static class Log {

        @Schema(description = "Log number", requiredMode = Schema.RequiredMode.REQUIRED, example = "8848")
        private Long id;

        @Schema(description = "Notification status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Byte status;

        @Schema(description = "Current notification count", requiredMode = Schema.RequiredMode.REQUIRED)
        private Byte notifyTimes;

        @Schema(description = "HTTP Response result", requiredMode = Schema.RequiredMode.REQUIRED)
        private String response;

        @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDateTime createTime;

    }

}
