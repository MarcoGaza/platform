package cn.econets.blossom.module.mp.controller.admin.news.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Pagination of public account draft Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpDraftPageReqVO extends PageParam {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The public account number cannot be empty")
    private Long accountId;

}
