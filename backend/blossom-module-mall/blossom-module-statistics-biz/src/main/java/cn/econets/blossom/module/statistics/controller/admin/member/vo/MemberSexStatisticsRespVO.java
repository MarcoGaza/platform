package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Member gender statistics Response VO")
@Data
public class MemberSexStatisticsRespVO {

    @Schema(description = "Gender", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sex;

    // TODO Or other fields，We also complete it，This is a user-friendly way to use it，Customization；Just keep peace MemberAreaStatisticsRespVO Consistent；
    @Schema(description = "Number of members", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer userCount;

}
