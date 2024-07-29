package cn.econets.blossom.module.crm.controller.admin.customer.vo.poolconfig;

import cn.hutool.core.util.BooleanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Schema(description = "Management Backend - CRM Creation of client high seas configuration/Update Request VO")
@Data
public class CrmCustomerPoolConfigSaveReqVO {

    @Schema(description = "Whether to enable client high seas", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @DiffLogField(name = "Whether to enable client high seas")
    @NotNull(message = "Whether to enable the client's high seas cannot be empty")
    private Boolean enabled;

    @Schema(description = "Number of days released into the high seas without follow-up", example = "2")
    @DiffLogField(name = "Number of days released into the high seas without follow-up")
    private Integer contactExpireDays;

    @Schema(description = "Number of days unsold items were placed on the high seas", example = "2")
    @DiffLogField(name = "Number of days unsold items were placed on the high seas")
    private Integer dealExpireDays;

    @Schema(description = "Whether to enable advance reminder", example = "true")
    @DiffLogField(name = "Whether to enable advance reminder")
    private Boolean notifyEnabled;

    @Schema(description = "Days of advance reminder", example = "2")
    @DiffLogField(name = "Days of advance reminder")
    private Integer notifyDays;

    @AssertTrue(message = "The number of days that unsold items are placed on the high seas cannot be empty")
    @JsonIgnore
    public boolean isDealExpireDaysValid() {
        if (!BooleanUtil.isTrue(getEnabled())) {
            return true;
        }
        return Objects.nonNull(getDealExpireDays());
    }

    @AssertTrue(message = "The number of days not followed up on the high seas cannot be empty")
    @JsonIgnore
    public boolean isContactExpireDaysValid() {
        if (!BooleanUtil.isTrue(getEnabled())) {
            return true;
        }
        return Objects.nonNull(getContactExpireDays());
    }

    @AssertTrue(message = "The number of days to remind in advance cannot be empty")
    @JsonIgnore
    public boolean isNotifyDaysValid() {
        if (!BooleanUtil.isTrue(getNotifyEnabled())) {
            return true;
        }
        return Objects.nonNull(getNotifyDays());
    }

}
