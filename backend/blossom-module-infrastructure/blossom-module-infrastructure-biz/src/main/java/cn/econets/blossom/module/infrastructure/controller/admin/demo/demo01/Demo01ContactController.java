package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo.Demo01ContactPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo.Demo01ContactRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo.Demo01ContactSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo01.Demo01ContactDO;
import cn.econets.blossom.module.infrastructure.service.demo.demo01.Demo01ContactService;
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

@Tag(name = "Management Backend - Sample Contact")
@RestController
@RequestMapping("/infra/demo01-contact")
@Validated
public class Demo01ContactController {

    @Resource
    private Demo01ContactService demo01ContactService;

    @PostMapping("/create")
    @Operation(summary = "Create sample contact")
    @PreAuthorize("@ss.hasPermission('infra:demo01-contact:create')")
    public CommonResult<Long> createDemo01Contact(@Valid @RequestBody Demo01ContactSaveReqVO createReqVO) {
        return success(demo01ContactService.createDemo01Contact(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update sample contact")
    @PreAuthorize("@ss.hasPermission('infra:demo01-contact:update')")
    public CommonResult<Boolean> updateDemo01Contact(@Valid @RequestBody Demo01ContactSaveReqVO updateReqVO) {
        demo01ContactService.updateDemo01Contact(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete sample contact")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo01-contact:delete')")
    public CommonResult<Boolean> deleteDemo01Contact(@RequestParam("id") Long id) {
        demo01ContactService.deleteDemo01Contact(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get sample contacts")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:demo01-contact:query')")
    public CommonResult<Demo01ContactRespVO> getDemo01Contact(@RequestParam("id") Long id) {
        Demo01ContactDO demo01Contact = demo01ContactService.getDemo01Contact(id);
        return success(BeanUtils.toBean(demo01Contact, Demo01ContactRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get sample contact paging")
    @PreAuthorize("@ss.hasPermission('infra:demo01-contact:query')")
    public CommonResult<PageResult<Demo01ContactRespVO>> getDemo01ContactPage(@Valid Demo01ContactPageReqVO pageReqVO) {
        PageResult<Demo01ContactDO> pageResult = demo01ContactService.getDemo01ContactPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, Demo01ContactRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export sample contacts Excel")
    @PreAuthorize("@ss.hasPermission('infra:demo01-contact:export')")
    @OperateLog(type = EXPORT)
    public void exportDemo01ContactExcel(@Valid Demo01ContactPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<Demo01ContactDO> list = demo01ContactService.getDemo01ContactPage(pageReqVO).getList();
        // Export Excel
        ExcelUtils.write(response, "Sample Contact.xls", "Data", Demo01ContactRespVO.class,
                        BeanUtils.toBean(list, Demo01ContactRespVO.class));
    }

}
