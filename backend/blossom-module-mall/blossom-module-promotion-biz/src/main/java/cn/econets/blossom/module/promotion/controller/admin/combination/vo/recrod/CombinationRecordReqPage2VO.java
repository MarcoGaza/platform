package cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Group buying record paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationRecordReqPage2VO extends PageParam {

    @Schema(description = "Team Leader Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The team leader number cannot be empty")
    private Long headId;

}
