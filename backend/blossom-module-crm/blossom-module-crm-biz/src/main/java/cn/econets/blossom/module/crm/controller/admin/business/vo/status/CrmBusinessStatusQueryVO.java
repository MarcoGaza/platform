package cn.econets.blossom.module.crm.controller.admin.business.vo.status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;

@Schema(description = "Management Backend - Opportunity Status Query VO")
@Data
@ToString(callSuper = true)
public class CrmBusinessStatusQueryVO {

    @Schema(description = "Primary key set")
    private Collection<Long> idList;

    @Schema(description = "Status type number")
    private Long typeId;
}
