package cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Management Backend - CRM Creating payment Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivableCreateReqVO extends CrmReceivableBaseVO {

}
