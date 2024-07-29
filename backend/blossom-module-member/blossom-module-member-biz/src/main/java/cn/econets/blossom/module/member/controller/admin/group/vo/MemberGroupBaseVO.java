package cn.econets.blossom.module.member.controller.admin.group.vo;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * User Grouping Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberGroupBaseVO {

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shopping Expert")
    @NotNull(message = "The name cannot be empty")
    private String name;

    @Schema(description = "Remarks", requiredMode = Schema.RequiredMode.REQUIRED, example = "Guess")
    private String remark;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

}
