package cn.econets.blossom.module.crm.controller.admin.business.vo.type;

import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusSaveReqVO;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Management Backend - New opportunity status type/Modify Request VO")
@Data
public class CrmBusinessStatusTypeSaveReqVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "2934")
    private Long id;

    @Schema(description = "Status type name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotEmpty(message = "The status type name cannot be empty")
    private String name;

    // TODO @lzxhqs： VO Inside，We don't use the default value。Here Lists.newArrayList() See how to remove it。Above deptIds It is also similar
    @Schema(description = "Department number used", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> deptIds = Lists.newArrayList();

    @Schema(description = "Opportunity status collection", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CrmBusinessStatusSaveReqVO> statusList;

}
