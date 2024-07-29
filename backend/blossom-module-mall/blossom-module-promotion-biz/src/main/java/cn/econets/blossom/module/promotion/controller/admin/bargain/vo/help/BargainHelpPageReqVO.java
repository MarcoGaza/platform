package cn.econets.blossom.module.promotion.controller.admin.bargain.vo.help;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Bargaining helps paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BargainHelpPageReqVO extends PageParam {

    @Schema(description = "Bargaining record number", example = "1800")
    private Long recordId;

}
