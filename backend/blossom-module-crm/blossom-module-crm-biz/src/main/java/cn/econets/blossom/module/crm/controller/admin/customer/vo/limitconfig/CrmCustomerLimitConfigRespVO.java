package cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig;

import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Customer restriction configuration Response VO")
@Data
public class CrmCustomerLimitConfigRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "27930")
    private Long id;

    @Schema(description = "Rule Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer type;

    @Schema(description = "People to whom the rules apply")
    private List<Long> userIds;

    @Schema(description = "Rules applicable departments")
    private List<Long> deptIds;

    @Schema(description = "Quantity limit", requiredMode = Schema.RequiredMode.REQUIRED, example = "28384")
    private Integer maxCount;

    @Schema(description = "Whether the transaction customer occupies the number of customers")
    private Boolean dealCountEnabled;

    @Schema(description = "Name of the people to whom the rules apply")
    private List<AdminUserRespDTO> users;

    @Schema(description = "Name of department to which the rule applies")
    private List<DeptRespDTO> depts;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
