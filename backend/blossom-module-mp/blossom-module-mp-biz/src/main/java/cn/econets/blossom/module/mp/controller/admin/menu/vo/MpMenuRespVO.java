package cn.econets.blossom.module.mp.controller.admin.menu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Official Account Menu Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpMenuRespVO extends MpMenuBaseVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long accountId;

    @Schema(description = "Public Account appId", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx1234567890ox")
    private String appId;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
