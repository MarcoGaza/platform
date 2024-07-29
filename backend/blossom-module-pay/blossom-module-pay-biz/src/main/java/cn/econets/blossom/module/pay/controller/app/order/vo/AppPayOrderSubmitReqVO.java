package cn.econets.blossom.module.pay.controller.app.order.vo;

import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderSubmitReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "User APP - Payment order submission Request VO")
@Data
public class AppPayOrderSubmitReqVO  extends PayOrderSubmitReqVO {
}
