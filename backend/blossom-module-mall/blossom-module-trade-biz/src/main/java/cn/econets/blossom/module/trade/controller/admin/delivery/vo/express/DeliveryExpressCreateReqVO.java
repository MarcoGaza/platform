package cn.econets.blossom.module.trade.controller.admin.delivery.vo.express;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Management Backend - Express delivery company created Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryExpressCreateReqVO extends DeliveryExpressBaseVO {

}
