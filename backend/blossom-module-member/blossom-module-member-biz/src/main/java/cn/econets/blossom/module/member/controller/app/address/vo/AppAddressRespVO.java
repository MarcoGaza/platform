package cn.econets.blossom.module.member.controller.app.address.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "User APP - User's mailing address Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppAddressRespVO extends AppAddressBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Region name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Putuo District, Shanghai")
    private String areaName;

}
