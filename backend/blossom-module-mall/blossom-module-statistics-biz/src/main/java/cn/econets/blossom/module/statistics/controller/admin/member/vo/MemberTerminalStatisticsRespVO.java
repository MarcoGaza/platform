package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Member terminal statistics Response VO")
@Data
public class MemberTerminalStatisticsRespVO {

    @Schema(description = "Terminal", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer terminal;

    // TODO Or orderCreateUserCount Japanese orderPayUserCount Seems more unified；
    @Schema(description = "Number of members", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer userCount;

}
