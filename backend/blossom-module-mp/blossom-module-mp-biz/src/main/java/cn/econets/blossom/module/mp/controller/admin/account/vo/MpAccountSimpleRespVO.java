package cn.econets.blossom.module.mp.controller.admin.account.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Public account simplified information Response VO")
@Data
public class MpAccountSimpleRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Public Account Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    private String name;

}
