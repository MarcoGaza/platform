package cn.econets.blossom.module.crm.controller.admin.business.vo.type;

import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Opportunity Status Type Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmBusinessStatusTypeRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "2934")
    @ExcelProperty("Primary key")
    private Long id;

    @Schema(description = "Status type name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @ExcelProperty("Status type name")
    private String name;

    @Schema(description = "Department number used", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Department number used")
    private List<Long> deptIds;
    @Schema(description = "Name of the department used", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Name of the department used")
    private List<String> deptNames;

    @Schema(description = "Creator", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creator")
    private String creator;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    // TODO @ljlleo Field suffix changed to statuses，Maintain peace deptIds Same style；CrmBusinessStatusDO Change to VO Ha；Generally not used do Return directly
    @Schema(description = "Status Collection", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CrmBusinessStatusDO> statusList;

}
