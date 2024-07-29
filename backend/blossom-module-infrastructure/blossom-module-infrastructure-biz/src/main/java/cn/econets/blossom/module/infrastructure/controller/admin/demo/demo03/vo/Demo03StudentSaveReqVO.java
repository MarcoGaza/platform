package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo;

import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03CourseDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03GradeDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - New students/Modify Request VO")
@Data
public class Demo03StudentSaveReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "8525")
    private Long id;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @Schema(description = "Gender", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Gender cannot be empty")
    private Integer sex;

    @Schema(description = "Date of Birth", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Date of birth cannot be empty")
    private LocalDateTime birthday;

    @Schema(description = "Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "Whatever")
    @NotEmpty(message = "Introduction cannot be empty")
    private String description;


    private List<Demo03CourseDO> demo03Courses;

    private Demo03GradeDO demo03Grade;

}
