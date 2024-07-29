package cn.econets.blossom.module.product.controller.admin.favorite.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Product Collection Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductFavoritePageReqVO extends PageParam {

    @Schema(description = "User Number", example = "5036")
    private Long userId;

}
