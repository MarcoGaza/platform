package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Members' Regional Statistics Response VO")
@Data
public class MemberAreaStatisticsRespVO {

    @Schema(description = "Province number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer areaId;
    @Schema(description = "Province Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhejiang Province")
    private String areaName;

    @Schema(description = "Number of members", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer userCount;

    @Schema(description = "Number of members who placed orders", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer orderCreateUserCount;
    @Schema(description = "Number of members who paid for the order", requiredMode = Schema.RequiredMode.REQUIRED, example = "512")
    private Integer orderPayUserCount;

    @Schema(description = "Order payment amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "622")
    private Integer orderPayPrice;

}
