package cn.econets.blossom.module.member.controller.admin.level.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Member level record Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberLevelRecordBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "25923")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "Level Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "25985")
    @NotNull(message = "Level number cannot be empty")
    private Long levelId;

    @Schema(description = "Member Level", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Member level cannot be empty")
    private Integer level;

    @Schema(description = "Enjoy discount", requiredMode = Schema.RequiredMode.REQUIRED, example = "13319")
    @NotNull(message = "Discount cannot be empty")
    private Integer discountPercent;

    @Schema(description = "Upgrade Experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "13319")
    @NotNull(message = "Upgrade experience cannot be empty")
    private Integer experience;

    @Schema(description = "Member's current experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "13319")
    @NotNull(message = "The member's experience at this time cannot be empty")
    private Integer userExperience;

    @Schema(description = "Remarks", requiredMode = Schema.RequiredMode.REQUIRED, example = "Promotion needs")
    @NotNull(message = "Remarks cannot be empty")
    private String remark;

    @Schema(description = "Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Upgrade to Gold Member")
    @NotNull(message = "Description cannot be empty")
    private String description;

}
