package cn.econets.blossom.module.promotion.controller.app.combination.vo.record;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.combination.CombinationRecordStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "User App - Group buying record paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppCombinationRecordPageReqVO extends PageParam {

    @Schema(description = "Group buying status", example = "1")
    @InEnum(value = CombinationRecordStatusEnum.class, message = "The group status must be {value}")
    private Integer status;

}
