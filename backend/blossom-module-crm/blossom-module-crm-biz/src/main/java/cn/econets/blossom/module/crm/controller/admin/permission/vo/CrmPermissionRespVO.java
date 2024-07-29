package cn.econets.blossom.module.crm.controller.admin.permission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Management Backend - CRM Data permissions Response VO")
@Data
public class CrmPermissionRespVO extends CrmPermissionBaseVO {

    @Schema(description = "Data permission number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    private Long id;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String nickname;

    @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "R&D Department")
    private String deptName;

    @Schema(description = "Position name array", requiredMode = Schema.RequiredMode.REQUIRED, example = "[BOOS,Manager]")
    private Set<String> postNames;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-01-01 00:00:00")
    private LocalDateTime createTime;

}
