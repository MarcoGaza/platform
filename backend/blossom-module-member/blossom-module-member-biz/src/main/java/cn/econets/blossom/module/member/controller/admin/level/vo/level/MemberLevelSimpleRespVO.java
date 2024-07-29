package cn.econets.blossom.module.member.controller.admin.level.vo.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "Management Backend - Member Level Response VO")
@Data
@ToString(callSuper = true)
public class MemberLevelSimpleRespVO {

    @Schema(description = "Number", example = "6103")
    private Long id;

    @Schema(description = "Level Name", example = "econets")
    private String name;

    @Schema(description = "Level Icon", example = "https://www.econets.cn/blossom.jpg")
    private String icon;

}
