package cn.econets.blossom.module.system.controller.admin.sms.vo.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - SMS channel creation/Modify Request VO")
@Data
public class SmsChannelSaveReqVO {

    @Schema(description = "Number", example = "1024")
    private Long id;

    @Schema(description = "SMS signature", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotNull(message = "SMS signature cannot be empty")
    private String signature;

    @Schema(description = "Channel Code，See SmsChannelEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "YUN_PIAN")
    @NotNull(message = "Channel code cannot be empty")
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

    @Schema(description = "SMS sending callback URL", example = "http://www.econets.cn")
    @URL(message = "Callback URL Incorrect format")
    private String callbackUrl;

}
