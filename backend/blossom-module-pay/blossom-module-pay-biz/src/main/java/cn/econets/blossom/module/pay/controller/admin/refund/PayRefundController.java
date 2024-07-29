package cn.econets.blossom.module.pay.controller.admin.refund;

import cn.hutool.core.collection.CollectionUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.pay.controller.admin.refund.vo.*;
import cn.econets.blossom.module.pay.convert.refund.PayRefundConvert;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.service.app.PayAppService;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - Refund order")
@RestController
@RequestMapping("/pay/refund")
@Validated
public class PayRefundController {

    @Resource
    private PayRefundService refundService;
    @Resource
    private PayAppService appService;

    @GetMapping("/get")
    @Operation(summary = "Get a refund order")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:refund:query')")
    public CommonResult<PayRefundDetailsRespVO> getRefund(@RequestParam("id") Long id) {
        PayRefundDO refund = refundService.getRefund(id);
        if (refund == null) {
            return success(new PayRefundDetailsRespVO());
        }

        // Splicing data
        PayAppDO app = appService.getApp(refund.getAppId());
        return success(PayRefundConvert.INSTANCE.convert(refund, app));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the refund order page")
    @PreAuthorize("@ss.hasPermission('pay:refund:query')")
    public CommonResult<PageResult<PayRefundPageItemRespVO>> getRefundPage(@Valid PayRefundPageReqVO pageVO) {
        PageResult<PayRefundDO> pageResult = refundService.getRefundPage(pageVO);
        if (CollectionUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }

        // Processing applicationIDData
        Map<Long, PayAppDO> appMap = appService.getAppMap(convertList(pageResult.getList(), PayRefundDO::getAppId));
        return success(PayRefundConvert.INSTANCE.convertPage(pageResult, appMap));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export refund orders Excel")
    @PreAuthorize("@ss.hasPermission('pay:refund:export')")
    @OperateLog(type = EXPORT)
    public void exportRefundExcel(@Valid PayRefundExportReqVO exportReqVO,
            HttpServletResponse response) throws IOException {
        List<PayRefundDO> list = refundService.getRefundList(exportReqVO);
        if (CollectionUtil.isEmpty(list)) {
            ExcelUtils.write(response, "Refund order.xls", "Data",
                    PayRefundExcelVO.class, new ArrayList<>());
            return;
        }

        // Splicing returns
        Map<Long, PayAppDO> appMap = appService.getAppMap(convertList(list, PayRefundDO::getAppId));
        List<PayRefundExcelVO> excelList = PayRefundConvert.INSTANCE.convertList(list, appMap);
        // Export Excel
        ExcelUtils.write(response, "Refund order.xls", "Data", PayRefundExcelVO.class, excelList);
    }

}
