package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Code generation preview Response VO，Attention，Each file is an object of this type")
@Data
public class CodegenPreviewRespVO {

    @Schema(description = "File path", requiredMode = Schema.RequiredMode.REQUIRED, example = "java/cn/econets/blossom/adminserver/modules/system/controller/test/SysTestDemoController.java")
    private String filePath;

    @Schema(description = "Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hello World")
    private String code;

}
