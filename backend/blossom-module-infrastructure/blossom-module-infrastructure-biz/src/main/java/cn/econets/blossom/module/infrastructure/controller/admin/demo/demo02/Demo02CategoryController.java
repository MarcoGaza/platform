package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo.Demo02CategoryListReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo.Demo02CategoryRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo02.vo.Demo02CategorySaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo02.Demo02CategoryDO;
import cn.econets.blossom.module.infrastructure.service.demo.demo02.Demo02CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - Example Category")
@RestController
@RequestMapping("/infra/demo02-category")
@Validated
public class Demo02CategoryController {

    @Resource
    private Demo02CategoryService demo02CategoryService;

    @PostMapping("/create")
    @Operation(summary = "Create sample category")
    @PreAuthorize("@ss.hasPermission('infra:demo02-category:create')")
    public CommonResult<Long> createDemo02Category(@Valid @RequestBody Demo02CategorySaveReqVO createReqVO) {
        return success(demo02CategoryService.createDemo02Category(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update example categories")
    @PreAuthorize("@ss.hasPermission('infra:demo02-category:update')")
    public CommonResult<Boolean> updateDemo02Category(@Valid @RequestBody Demo02CategorySaveReqVO updateReqVO) {
        demo02CategoryService.updateDemo02Category(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete example category")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo02-category:delete')")
    public CommonResult<Boolean> deleteDemo02Category(@RequestParam("id") Long id) {
        demo02CategoryService.deleteDemo02Category(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get sample classification")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:demo02-category:query')")
    public CommonResult<Demo02CategoryRespVO> getDemo02Category(@RequestParam("id") Long id) {
        Demo02CategoryDO demo02Category = demo02CategoryService.getDemo02Category(id);
        return success(BeanUtils.toBean(demo02Category, Demo02CategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "Get a list of example categories")
    @PreAuthorize("@ss.hasPermission('infra:demo02-category:query')")
    public CommonResult<List<Demo02CategoryRespVO>> getDemo02CategoryList(@Valid Demo02CategoryListReqVO listReqVO) {
        List<Demo02CategoryDO> list = demo02CategoryService.getDemo02CategoryList(listReqVO);
        return success(BeanUtils.toBean(list, Demo02CategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export example classification Excel")
    @PreAuthorize("@ss.hasPermission('infra:demo02-category:export')")
    @OperateLog(type = EXPORT)
    public void exportDemo02CategoryExcel(@Valid Demo02CategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<Demo02CategoryDO> list = demo02CategoryService.getDemo02CategoryList(listReqVO);
        // Export Excel
        ExcelUtils.write(response, "Example Category.xls", "Data", Demo02CategoryRespVO.class,
                        BeanUtils.toBean(list, Demo02CategoryRespVO.class));
    }

}
