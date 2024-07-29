package cn.econets.blossom.module.product.controller.admin.property.vo.value;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Product attribute value paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductPropertyValuePageReqVO extends PageParam {

    @Schema(description = "The number of the property item", example = "1024")
    private String propertyId;

    @Schema(description = "Name", example = "Red")
    private String name;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

}
