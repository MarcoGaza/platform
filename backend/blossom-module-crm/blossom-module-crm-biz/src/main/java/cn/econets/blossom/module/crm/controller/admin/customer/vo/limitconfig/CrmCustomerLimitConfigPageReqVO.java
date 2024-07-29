package cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Customer restriction configuration paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmCustomerLimitConfigPageReqVO extends PageParam {

    @Schema(description = "Rule Type", example = "1")
    private Integer type;

}
