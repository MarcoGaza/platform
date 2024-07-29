package cn.econets.blossom.module.member.controller.admin.level.vo.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "Management Backend - Member level list filter Request VO")
@Data
@ToString(callSuper = true)
public class MemberLevelListReqVO {

    @Schema(description = "Level Name", example = "econets")
    private String name;

    @Schema(description = "Status", example = "1")
    private Integer status;

}
