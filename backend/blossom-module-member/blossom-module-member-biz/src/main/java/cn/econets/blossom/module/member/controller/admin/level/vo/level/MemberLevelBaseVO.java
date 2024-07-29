package cn.econets.blossom.module.member.controller.admin.level.vo.level;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Member Level Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberLevelBaseVO {

    @Schema(description = "Level Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @NotBlank(message = "Level name cannot be empty")
    private String name;

    @Schema(description = "Upgrade Experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "Upgrade experience cannot be empty")
    @Positive(message = "Upgrade experience must be greater than 0")
    private Integer experience;

    @Schema(description = "Level", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Level cannot be empty")
    @Positive(message = "Level must be greater than 0")
    private Integer level;

    @Schema(description = "Enjoy discount", requiredMode = Schema.RequiredMode.REQUIRED, example = "98")
    @NotNull(message = "The discount value cannot be empty")
    @Range(min = 0, max = 100, message = "The discount range is 0-100")
    private Integer discountPercent;

    @Schema(description = "Level Icon", example = "https://www.econets.cn/blossom.jpg")
    @URL(message = "Level icon must be URL Format")
    private String icon;

    @Schema(description = "Level background image", example = "https://www.econets.cn/blossom.jpg")
    @URL(message = "Level background image must be URL Format")
    private String backgroundUrl;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

}
