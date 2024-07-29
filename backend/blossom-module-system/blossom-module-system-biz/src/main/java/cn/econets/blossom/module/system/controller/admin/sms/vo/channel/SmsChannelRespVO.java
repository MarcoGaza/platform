package cn.econets.blossom.module.system.controller.admin.sms.vo.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - SMS channel Response VO")
@Data
public class SmsChannelRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "SMS signature", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotNull(message = "SMS signature cannot be empty")
    private String signature;

    @Schema(description = "Channel Code，See SmsChannelEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "YUN_PIAN")
    private String code;

    @Schema(description = "Enabled status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Enabled status cannot be empty")
    private Integer status;

    @Schema(description = "Remarks", example = "Delicious！")
    private String remark;

    @Schema(description = "SMS API Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotNull(message = "SMS API The account cannot be empty")
    private String apiKey;

    @Schema(description = "SMS API Key", example = "yuanma")
    private String apiSecret;

    @Schema(description = "SMS sending callback URL", example = "https://www.econets.cn")
    @URL(message = "Callback URL Incorrect format")
    private String callbackUrl;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
