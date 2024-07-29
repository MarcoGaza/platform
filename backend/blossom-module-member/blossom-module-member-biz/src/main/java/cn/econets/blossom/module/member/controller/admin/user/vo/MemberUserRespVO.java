package cn.econets.blossom.module.member.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Member User Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserRespVO extends MemberUserBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23788")
    private Long id;

    @Schema(description = "Register IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    private String registerIp;

    @Schema(description = "Last loginIP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    private String loginIp;

    @Schema(description = "Last login time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime loginDate;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== Other information ==========

    @Schema(description = "Points", requiredMode  = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer point;

    @Schema(description = "Total points", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000")
    private Integer totalPoint;

    @Schema(description = "Member Tag", example = "[Red, Happy]")
    private List<String> tagNames;

    @Schema(description = "Member Level", example = "Gold Member")
    private String levelName;

    @Schema(description = "User Grouping", example = "Shopping Expert")
    private String groupName;

    @Schema(description = "User Experience Value", requiredMode  = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer experience;

}
