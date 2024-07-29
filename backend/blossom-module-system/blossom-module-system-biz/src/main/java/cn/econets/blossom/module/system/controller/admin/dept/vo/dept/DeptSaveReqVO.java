package cn.econets.blossom.module.system.controller.admin.dept.vo.dept;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Department Creation/Modify Request VO")
@Data
public class DeptSaveReqVO {

    @Schema(description = "Department Number", example = "1024")
    private Long id;

    @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotBlank(message = "Department name cannot be empty")
    @Size(max = 30, message = "The length of the department name cannot exceed 30 Characters")
    private String name;

    @Schema(description = "Parent Department ID", example = "1024")
    private Long parentId;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The display order cannot be empty")
    private Integer sort;

    @Schema(description = "User ID of the person in charge", example = "2048")
    private Long leaderUserId;

    @Schema(description = "Contact number", example = "15601691000")
    @Size(max = 11, message = "The contact number cannot exceed11Characters")
    private String phone;

    @Schema(description = "Mailbox", example = "ryximu@qq.com")
    @Email(message = "The email format is incorrect")
    @Size(max = 50, message = "The length of the email address cannot exceed 50 Characters")
    private String email;

    @Schema(description = "Status,See you CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The modification status must be {value}")
    private Integer status;

}
