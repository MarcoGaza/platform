package cn.econets.blossom.module.member.controller.admin.point.vo.recrod;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - User Points Record Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberPointRecordPageReqVO extends PageParam {

    @Schema(description = "User Nickname", example = "Zhang San")
    private String nickname;

    @Schema(description = "User Number", example = "123")
    private Long userId;

    @Schema(description = "Business Type", example = "1")
    private Integer bizType;

    @Schema(description = "Points Title", example = "Hehe")
    private String title;

}
