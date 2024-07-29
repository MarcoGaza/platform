package cn.econets.blossom.module.system.controller.admin.user.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Management Backend - User simplified information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleRespVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String nickname;

    @Schema(description = "DepartmentID", example = "I am a user")
    private Long deptId;
    @Schema(description = "Department Name", example = "IT Department")
    private String deptName;

}
