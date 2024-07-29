package cn.econets.blossom.module.trade.controller.admin.aftersale;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.pay.api.notify.dto.PayRefundNotifyReqDTO;
import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.*;
import cn.econets.blossom.module.trade.convert.aftersale.AfterSaleConvert;
import cn.econets.blossom.module.trade.dal.dataobject.aftersale.AfterSaleDO;
import cn.econets.blossom.module.trade.dal.dataobject.aftersale.AfterSaleLogDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.service.aftersale.AfterSaleLogService;
import cn.econets.blossom.module.trade.service.aftersale.AfterSaleService;
import cn.econets.blossom.module.trade.service.order.TradeOrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - After-sales order")
@RestController
@RequestMapping("/trade/after-sale")
@Validated
@Slf4j
public class AfterSaleController {

    @Resource
    private AfterSaleService afterSaleService;
    @Resource
    private TradeOrderQueryService tradeOrderQueryService;
    @Resource
    private AfterSaleLogService afterSaleLogService;
    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/page")
    @Operation(summary = "Get after-sales order paging")
    @PreAuthorize("@ss.hasPermission('trade:after-sale:query')")
    public CommonResult<PageResult<AfterSaleRespPageItemVO>> getAfterSalePage(@Valid AfterSalePageReqVO pageVO) {
        // After-sales enquiry
        PageResult<AfterSaleDO> pageResult = afterSaleService.getAfterSalePage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }

        // Query members
        Map<Long, MemberUserRespDTO> memberUsers = memberUserApi.getUserMap(
                convertSet(pageResult.getList(), AfterSaleDO::getUserId));
        return success(AfterSaleConvert.INSTANCE.convertPage(pageResult, memberUsers));
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get after-sales order details")
    @Parameter(name = "id", description = "After-sales number", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('trade:after-sale:query')")
    public CommonResult<AfterSaleDetailRespVO> getOrderDetail(@RequestParam("id") Long id) {
        // Query order
        AfterSaleDO afterSale = afterSaleService.getAfterSale(id);
        if (afterSale == null) {
            return success(null);
        }

        // Query order
        TradeOrderDO order = tradeOrderQueryService.getOrder(afterSale.getOrderId());
        // Query order items
        TradeOrderItemDO orderItem = tradeOrderQueryService.getOrderItem(afterSale.getOrderItemId());
        // Splicing data
        MemberUserRespDTO user = memberUserApi.getUser(afterSale.getUserId());
        List<AfterSaleLogDO> logs = afterSaleLogService.getAfterSaleLogList(afterSale.getId());
        return success(AfterSaleConvert.INSTANCE.convert(afterSale, order, orderItem, user, logs));
    }

    @PutMapping("/agree")
    @Operation(summary = "Agree to after-sales service")
    @Parameter(name = "id", description = "After-sales number", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('trade:after-sale:agree')")
    public CommonResult<Boolean> agreeAfterSale(@RequestParam("id") Long id) {
        afterSaleService.agreeAfterSale(getLoginUserId(), id);
        return success(true);
    }

    @PutMapping("/disagree")
    @Operation(summary = "Refuse after-sales service")
    @PreAuthorize("@ss.hasPermission('trade:after-sale:disagree')")
    public CommonResult<Boolean> disagreeAfterSale(@RequestBody AfterSaleDisagreeReqVO confirmReqVO) {
        afterSaleService.disagreeAfterSale(getLoginUserId(), confirmReqVO);
        return success(true);
    }

    @PutMapping("/receive")
    @Operation(summary = "Confirm receipt")
    @Parameter(name = "id", description = "After-sales number", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('trade:after-sale:receive')")
    public CommonResult<Boolean> receiveAfterSale(@RequestParam("id") Long id) {
        afterSaleService.receiveAfterSale(getLoginUserId(), id);
        return success(true);
    }

    @PutMapping("/refuse")
    @Operation(summary = "Refuse to accept the goods")
    @Parameter(name = "id", description = "After-sales number", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('trade:after-sale:receive')")
    public CommonResult<Boolean> refuseAfterSale(AfterSaleRefuseReqVO refuseReqVO) {
        afterSaleService.refuseAfterSale(getLoginUserId(), refuseReqVO);
        return success(true);
    }

    @PutMapping("/refund")
    @Operation(summary = "Confirm refund")
    @Parameter(name = "id", description = "After-sales number", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('trade:after-sale:refund')")
    public CommonResult<Boolean> refundAfterSale(@RequestParam("id") Long id) {
        afterSaleService.refundAfterSale(getLoginUserId(), getClientIP(), id);
        return success(true);
    }

    @PostMapping("/update-refunded")
    @Operation(summary = "Update after-sales order to refunded") // By pay-module Payment Service，Make callback，Visible PayNotifyJob
    @PermitAll // No need to log in，Safety by PayDemoOrderService Internal verification implementation
    @OperateLog(enable = false) // Disable operation log，Because there is no operator
    public CommonResult<Boolean> updateAfterRefund(@RequestBody PayRefundNotifyReqDTO notifyReqDTO) {
        // Current business logic，No need to do anything
        // Of course，There is a small chance that a refund may fail，Can monitor failure status，Produce an alarm
        log.info("[updateAfterRefund][notifyReqDTO({})]", notifyReqDTO);
        return success(true);
    }

}
