package cn.econets.blossom.module.member.controller.app.address.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "User APP - Create user recipient address Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppAddressCreateReqVO extends AppAddressBaseVO {

}
