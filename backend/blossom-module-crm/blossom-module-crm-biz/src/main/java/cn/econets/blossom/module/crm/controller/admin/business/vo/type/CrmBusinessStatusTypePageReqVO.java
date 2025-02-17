package cn.econets.blossom.module.crm.controller.admin.business.vo.type;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Opportunity status type paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmBusinessStatusTypePageReqVO extends PageParam {

}
