package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo;

import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.column.CodegenColumnRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.CodegenTableRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Management Backend - Details of code-generated tables and fields Response VO")
@Data
public class CodegenDetailRespVO {

    @Schema(description = "Table definition")
    private CodegenTableRespVO table;

    @Schema(description = "Field definition")
    private List<CodegenColumnRespVO> columns;

}
