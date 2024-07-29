package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo;

import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.column.CodegenColumnSaveReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.CodegenTableSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Modification of code-generated tables and fields Request VO")
@Data
public class CodegenUpdateReqVO {

    @Valid // Validate embedded fields
    @NotNull(message = "Table definition cannot be empty")
    private CodegenTableSaveReqVO table;

    @Valid // Validate embedded fields
    @NotNull(message = "Field definition cannot be empty")
    private List<CodegenColumnSaveReqVO> columns;

}
