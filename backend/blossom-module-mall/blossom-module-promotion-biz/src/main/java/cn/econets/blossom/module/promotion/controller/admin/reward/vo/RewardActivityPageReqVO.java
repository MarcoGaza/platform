package cn.econets.blossom.module.promotion.controller.admin.reward.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Paging for discounts and free gifts Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RewardActivityPageReqVO extends PageParam {

    @Schema(description = "Activity Title", example = "Manila Manila")
    private String name;

    @Schema(description = "Activity Status", example = "1")
    private Integer status;

}
