package cn.econets.blossom.module.member.controller.admin.signin.vo.config;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Management Backend - Creating sign-in rules Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberSignInConfigCreateReqVO extends MemberSignInConfigBaseVO {

}
