package cn.econets.blossom.module.infrastructure.controller.admin.codegen;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenCreateListReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenDetailRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenPreviewRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenUpdateReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.CodegenTableRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.DatabaseTableRespVO;
import cn.econets.blossom.module.infrastructure.convert.codegen.CodegenConvert;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenColumnDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenTableDO;
import cn.econets.blossom.module.infrastructure.service.codegen.CodegenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - Code generator")
@RestController
@RequestMapping("/infra/codegen")
@Validated
public class CodegenController {

    @Resource
    private CodegenService codegenService;

    @GetMapping("/db/table/list")
    @Operation(summary = "Get the table definition list that comes with the database", description = "Will filter out the imported ones Codegen Table")
    @Parameters({
            @Parameter(name = "dataSourceConfigId", description = "Data source configuration number", required = true, example = "1"),
            @Parameter(name = "name", description = "Table name，Fuzzy matching", example = "Table name"),
            @Parameter(name = "comment", description = "Description，Fuzzy matching", example = "Description")
    })
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<List<DatabaseTableRespVO>> getDatabaseTableList(
            @RequestParam(value = "dataSourceConfigId") Long dataSourceConfigId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "comment", required = false) String comment) {
        return success(codegenService.getDatabaseTableList(dataSourceConfigId, name, comment));
    }

    @GetMapping("/table/list")
    @Operation(summary = "Get table definition list")
    @Parameter(name = "dataSourceConfigId", description = "Data source configuration number", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<List<CodegenTableRespVO>> getCodegenTableList(@RequestParam(value = "dataSourceConfigId") Long dataSourceConfigId) {
        List<CodegenTableDO> list = codegenService.getCodegenTableList(dataSourceConfigId);
        return success(BeanUtils.toBean(list, CodegenTableRespVO.class));
    }

    @GetMapping("/table/page")
    @Operation(summary = "Get table definition paging")
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<PageResult<CodegenTableRespVO>> getCodegenTablePage(@Valid CodegenTablePageReqVO pageReqVO) {
        PageResult<CodegenTableDO> pageResult = codegenService.getCodegenTablePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CodegenTableRespVO.class));
    }

    @GetMapping("/detail")
    @Operation(summary = "Get table and field details")
    @Parameter(name = "tableId", description = "Table number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:query')")
    public CommonResult<CodegenDetailRespVO> getCodegenDetail(@RequestParam("tableId") Long tableId) {
        CodegenTableDO table = codegenService.getCodegenTable(tableId);
        List<CodegenColumnDO> columns = codegenService.getCodegenColumnListByTableId(tableId);
        // Assemble and return
        return success(CodegenConvert.INSTANCE.convert(table, columns));
    }

    @Operation(summary = "Based on the table structure of the database，Create table and field definitions for code generator")
    @PostMapping("/create-list")
    @PreAuthorize("@ss.hasPermission('infra:codegen:create')")
    public CommonResult<List<Long>> createCodegenList(@Valid @RequestBody CodegenCreateListReqVO reqVO) {
        return success(codegenService.createCodegenList(getLoginUserId(), reqVO));
    }

    @Operation(summary = "Update database table and field definitions")
    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('infra:codegen:update')")
    public CommonResult<Boolean> updateCodegen(@Valid @RequestBody CodegenUpdateReqVO updateReqVO) {
        codegenService.updateCodegen(updateReqVO);
        return success(true);
    }

    @Operation(summary = "Based on the table structure of the database，Synchronize database table and field definitions")
    @PutMapping("/sync-from-db")
    @Parameter(name = "tableId", description = "Table number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:update')")
    public CommonResult<Boolean> syncCodegenFromDB(@RequestParam("tableId") Long tableId) {
        codegenService.syncCodegenFromDB(tableId);
        return success(true);
    }

    @Operation(summary = "Delete the table and field definitions of the database")
    @DeleteMapping("/delete")
    @Parameter(name = "tableId", description = "Table number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:delete')")
    public CommonResult<Boolean> deleteCodegen(@RequestParam("tableId") Long tableId) {
        codegenService.deleteCodegen(tableId);
        return success(true);
    }

    @Operation(summary = "Preview generated code")
    @GetMapping("/preview")
    @Parameter(name = "tableId", description = "Table number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:preview')")
    public CommonResult<List<CodegenPreviewRespVO>> previewCodegen(@RequestParam("tableId") Long tableId) {
        Map<String, String> codes = codegenService.generationCodes(tableId);
        return success(CodegenConvert.INSTANCE.convert(codes));
    }

    @Operation(summary = "Download generated code")
    @GetMapping("/download")
    @Parameter(name = "tableId", description = "Table number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:codegen:download')")
    public void downloadCodegen(@RequestParam("tableId") Long tableId,
                                HttpServletResponse response) throws IOException {
        // Generate code
        Map<String, String> codes = codegenService.generationCodes(tableId);
        // Build zip Package
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // Output
        ServletUtils.writeAttachment(response, "codegen.zip", outputStream.toByteArray());
    }

}
