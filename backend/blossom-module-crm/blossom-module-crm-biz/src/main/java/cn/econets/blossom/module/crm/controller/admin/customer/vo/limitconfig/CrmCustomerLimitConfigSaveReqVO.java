package cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig;

import cn.econets.blossom.module.crm.framework.operatelog.core.SysAdminUserParseFunction;
import cn.econets.blossom.module.crm.framework.operatelog.core.SysDeptParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Customer restriction configuration creation/Update Request VO")
@Data
public class CrmCustomerLimitConfigSaveReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "27930")
    private Long id;

    @Schema(description = "Rule Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Rule type cannot be empty")
    @DiffLogField(name = "Rule Type")
    private Integer type;

    @Schema(description = "People to whom the rules apply")
    @DiffLogField(name = "People to whom the rules apply", function = SysAdminUserParseFunction.NAME)
    private List<Long> userIds;

    @Schema(description = "Rules applicable departments")
    @DiffLogField(name = "Rules applicable departments", function = SysDeptParseFunction.NAME)
    private List<Long> deptIds;

    @Schema(description = "Quantity limit", requiredMode = Schema.RequiredMode.REQUIRED, example = "28384")
    @NotNull(message = "The upper limit of quantity cannot be empty")
    @DiffLogField(name = "Quantity limit")
    private Integer maxCount;

    @Schema(description = "Whether the transaction customer occupies the number of customers(When type = 1 Time)")
    @DiffLogField(name = "Whether the transaction customer occupies the number of customers")
    private Boolean dealCountEnabled;

}
