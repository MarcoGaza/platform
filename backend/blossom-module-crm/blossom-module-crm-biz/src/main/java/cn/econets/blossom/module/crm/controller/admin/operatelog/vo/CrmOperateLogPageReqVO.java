package cn.econets.blossom.module.crm.controller.admin.operatelog.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - CRM Operation log Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmOperateLogPageReqVO extends PageParam {

    @Schema(description = "Data type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmBizTypeEnum.class)
    @NotNull(message = "Data type cannot be empty")
    private Integer bizType;

    @Schema(description = "Data number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Data number cannot be empty")
    private Long bizId;

}
