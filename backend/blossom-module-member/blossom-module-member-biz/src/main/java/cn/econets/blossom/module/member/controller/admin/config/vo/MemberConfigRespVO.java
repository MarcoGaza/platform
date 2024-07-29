package cn.econets.blossom.module.member.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Member Configuration Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberConfigRespVO extends MemberConfigBaseVO {

    @Schema(description = "Auto-increment primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

}
