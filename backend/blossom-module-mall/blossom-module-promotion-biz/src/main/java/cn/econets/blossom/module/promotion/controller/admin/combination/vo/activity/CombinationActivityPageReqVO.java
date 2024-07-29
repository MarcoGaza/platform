package cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Group buying activity page Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationActivityPageReqVO extends PageParam {

    @Schema(description = "Group name", example = "Zhao Liu")
    private String name;

    @Schema(description = "Activity Status", example = "0")
    private Integer status;

}
