package cn.econets.blossom.module.system.controller.admin.user.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "Management Backend - User Import Response VO")
@Data
@Builder
public class UserImportRespVO {

    @Schema(description = "Created a successful user name array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createUsernames;

    @Schema(description = "Updated successfully user name array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateUsernames;

    @Schema(description = "Import failed user collection，key Username，value Reason for failure", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureUsernames;

}
