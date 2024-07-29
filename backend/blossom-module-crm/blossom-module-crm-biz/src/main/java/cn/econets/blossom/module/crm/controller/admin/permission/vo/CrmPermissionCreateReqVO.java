package cn.econets.blossom.module.crm.controller.admin.permission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - CRM Data permission creation Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmPermissionCreateReqVO extends CrmPermissionBaseVO {

}
