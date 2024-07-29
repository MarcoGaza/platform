package cn.econets.blossom.module.member.controller.admin.address.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - User's mailing address Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AddressRespVO extends AddressBaseVO {

    @Schema(description = "Receiving address number", requiredMode = Schema.RequiredMode.REQUIRED, example = "7380")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
