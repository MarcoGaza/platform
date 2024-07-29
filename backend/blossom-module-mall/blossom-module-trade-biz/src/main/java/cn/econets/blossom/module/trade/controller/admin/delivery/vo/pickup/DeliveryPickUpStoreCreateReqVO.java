package cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "Management Backend - Self-pickup store creation Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryPickUpStoreCreateReqVO extends DeliveryPickUpStoreBaseVO {

}
