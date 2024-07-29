package cn.econets.blossom.module.pay.controller.admin.app.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Management Backend - Payment application information creation Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayAppCreateReqVO extends PayAppBaseVO {

}
