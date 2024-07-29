package cn.econets.blossom.module.member.controller.admin.signin.vo.config;

import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Sign-in rules Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberSignInConfigBaseVO {

    @Schema(description = "Sign in x Sky", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    @NotNull(message = "Sign-in days cannot be empty")
    private Integer day;

    @Schema(description = "Reward points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "Reward points cannot be empty")
    @PositiveOrZero(message = "Reward points cannot be less than 0")
    private Integer point;

    @Schema(description = "Reward experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "Reward experience cannot be empty")
    @PositiveOrZero(message = "Reward experience cannot be less than 0")
    private Integer experience;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @AssertTrue(message = "Sign-in bonus points and experience cannot be empty at the same time")
    @JsonIgnore
    public boolean isConfigAward() {
        return ObjUtil.notEqual(point, 0) || ObjUtil.notEqual(experience, 0);
    }
}
