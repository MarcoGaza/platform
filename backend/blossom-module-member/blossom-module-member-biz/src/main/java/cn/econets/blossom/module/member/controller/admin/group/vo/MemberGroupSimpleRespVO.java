package cn.econets.blossom.module.member.controller.admin.group.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "Management Backend - User Grouping Response VO")
@Data
@ToString(callSuper = true)
public class MemberGroupSimpleRespVO {

    @Schema(description = "Number", example = "6103")
    private Long id;

    @Schema(description = "Level Name", example = "econets")
    private String name;

}
