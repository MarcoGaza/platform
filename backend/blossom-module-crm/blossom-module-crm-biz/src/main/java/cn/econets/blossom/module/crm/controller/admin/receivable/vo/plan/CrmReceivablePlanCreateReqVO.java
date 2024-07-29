package cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Management Backend - CRM Creating a payment collection plan Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivablePlanCreateReqVO extends CrmReceivablePlanBaseVO {

}
