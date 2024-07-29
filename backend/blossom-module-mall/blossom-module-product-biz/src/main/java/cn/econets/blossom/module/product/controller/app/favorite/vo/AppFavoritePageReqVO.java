package cn.econets.blossom.module.product.controller.app.favorite.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Product collection page query Request VO")
@Data
public class AppFavoritePageReqVO extends PageParam {
}
