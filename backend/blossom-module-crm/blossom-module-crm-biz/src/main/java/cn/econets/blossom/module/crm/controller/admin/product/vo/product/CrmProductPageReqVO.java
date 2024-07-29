package cn.econets.blossom.module.crm.controller.admin.product.vo.product;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - CRM Product Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmProductPageReqVO extends PageParam {

    @Schema(description = "Product Name", example = "Li Si")
    private String name;

    @Schema(description = "Status", example = "1")
    private Integer status;

}
