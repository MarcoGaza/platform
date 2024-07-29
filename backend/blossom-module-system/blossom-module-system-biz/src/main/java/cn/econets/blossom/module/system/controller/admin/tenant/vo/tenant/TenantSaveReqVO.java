package cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Tenant creation/Modify Request VO")
@Data
public class TenantSaveReqVO {

    @Schema(description = "Tenant Number", example = "1024")
    private Long id;

    @Schema(description = "Tenant Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotNull(message = "Tenant name cannot be empty")
    private String name;

    @Schema(description = "Contact Person", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @NotNull(message = "Contact person cannot be empty")
    private String contactName;

    @Schema(description = "Contact phone number", example = "15601691300")
    private String contactMobile;

    @Schema(description = "Tenant status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Tenant Status")
    private Integer status;

    @Schema(description = "Bind domain name", example = "https://www.econets.cn")
    private String website;

    @Schema(description = "Tenant package number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Tenant package number cannot be empty")
    private Long packageId;

    @Schema(description = "Expiration time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The expiration date cannot be empty")
    private LocalDateTime expireTime;

    @Schema(description = "Number of accounts", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The number of accounts cannot be empty")
    private Integer accountCount;

    // ========== Only【Create】Time，Fields that need to be passed ==========

    @Schema(description = "User Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "User account by Number、Letters Composition")
    @Size(min = 4, max = 30, message = "User account length is 4-30 Characters")
    private String username;

    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @Length(min = 4, max = 16, message = "The password length is 4-16 position")
    private String password;

    @AssertTrue(message = "User Account、The password cannot be empty")
    @JsonIgnore
    public boolean isUsernameValid() {
        return id != null // When modifying，No need to pass
                || (ObjectUtil.isAllNotEmpty(username, password)); // When adding，Must pass all username、password
    }

}
