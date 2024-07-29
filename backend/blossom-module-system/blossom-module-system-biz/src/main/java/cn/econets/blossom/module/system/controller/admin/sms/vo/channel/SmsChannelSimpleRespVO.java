package cn.econets.blossom.module.system.controller.admin.sms.vo.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - SMS channel streamlining Response VO")
@Data
public class SmsChannelSimpleRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "SMS signature", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String signature;

    @Schema(description = "Channel Code，See SmsChannelEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "YUN_PIAN")
    private String code;

}
