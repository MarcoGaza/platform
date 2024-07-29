package cn.econets.blossom.module.crm.controller.admin.business.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;

@Schema(description = "Management Backend - Opportunity status type Query VO")
@Data
@ToString(callSuper = true)
public class CrmBusinessStatusTypeQueryVO {

    @Schema(description = "Primary key set")
    private Collection<Long> idList;

    @Schema(description = "Status")
    private Integer status;
}
