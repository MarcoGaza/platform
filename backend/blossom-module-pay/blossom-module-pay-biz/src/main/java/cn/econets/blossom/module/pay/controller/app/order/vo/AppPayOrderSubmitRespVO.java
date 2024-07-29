package cn.econets.blossom.module.pay.controller.app.order.vo;

import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderSubmitRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Schema(description = "User APP - Payment order submission Response VO")
@Data
public class AppPayOrderSubmitRespVO extends PayOrderSubmitRespVO {

}
