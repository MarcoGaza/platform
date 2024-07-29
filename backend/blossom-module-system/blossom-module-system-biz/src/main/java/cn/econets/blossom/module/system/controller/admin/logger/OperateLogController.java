package cn.econets.blossom.module.system.controller.admin.logger;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import cn.econets.blossom.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import cn.econets.blossom.module.system.controller.admin.logger.vo.operatelog.OperateLogRespVO;
import cn.econets.blossom.module.system.convert.logger.OperateLogConvert;
import cn.econets.blossom.module.system.dal.dataobject.logger.OperateLogDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.service.logger.OperateLogService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Operation log")
@RestController
@RequestMapping("/system/operate-log")
@Validated
public class OperateLogController {

    @Resource
    private OperateLogService operateLogService;
    @Resource
    private AdminUserService userService;

    @GetMapping("/page")
    @Operation(summary = "View the paginated list of operation logs")
    @PreAuthorize("@ss.hasPermission('system:operate-log:query')")
    public CommonResult<PageResult<OperateLogRespVO>> pageOperateLog(@Valid OperateLogPageReqVO pageReqVO) {
        PageResult<OperateLogDO> pageResult = operateLogService.getOperateLogPage(pageReqVO);
        // Get the data needed for splicing
        Map<Long, AdminUserDO> userMap = userService.getUserMap(
                CollectionUtils.convertList(pageResult.getList(), OperateLogDO::getUserId));
        return success(new PageResult<>(OperateLogConvert.INSTANCE.convertList(pageResult.getList(), userMap),
                pageResult.getTotal()));
    }

    @Operation(summary = "Export operation log")
    @GetMapping("/export")
    @PreAuthorize("@ss.hasPermission('system:operate-log:export')")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void exportOperateLog(HttpServletResponse response, @Valid OperateLogPageReqVO exportReqVO) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperateLogDO> list = operateLogService.getOperateLogPage(exportReqVO).getList();
        // Output
        Map<Long, AdminUserDO> userMap = userService.getUserMap(
                CollectionUtils.convertList(list, OperateLogDO::getUserId));
        ExcelUtils.write(response, "Operation log.xls", "Data list", OperateLogRespVO.class,
                OperateLogConvert.INSTANCE.convertList(list, userMap));
    }

}
