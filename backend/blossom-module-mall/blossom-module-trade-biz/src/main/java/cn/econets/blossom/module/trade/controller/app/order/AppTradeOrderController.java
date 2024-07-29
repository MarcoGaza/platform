package cn.econets.blossom.module.trade.controller.app.order;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.econets.blossom.module.trade.controller.app.order.vo.*;
import cn.econets.blossom.module.trade.controller.app.order.vo.item.AppTradeOrderItemCommentCreateReqVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.item.AppTradeOrderItemRespVO;
import cn.econets.blossom.module.trade.convert.order.TradeOrderConvert;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import cn.econets.blossom.module.trade.framework.order.config.TradeOrderProperties;
import cn.econets.blossom.module.trade.service.aftersale.AfterSaleService;
import cn.econets.blossom.module.trade.service.delivery.DeliveryExpressService;
import cn.econets.blossom.module.trade.service.order.TradeOrderQueryService;
import cn.econets.blossom.module.trade.service.order.TradeOrderUpdateService;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User App - Transaction Order")
@RestController
@RequestMapping("/trade/order")
@Validated
@Slf4j
public class AppTradeOrderController {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;
    @Resource
    private TradeOrderQueryService tradeOrderQueryService;
    @Resource
    private DeliveryExpressService deliveryExpressService;

    @Resource
    private AfterSaleService afterSaleService;

    @Resource
    private TradeOrderProperties tradeOrderProperties;

    @GetMapping("/settlement")
    @Operation(summary = "Get order settlement information")
    @PreAuthenticated
    public CommonResult<AppTradeOrderSettlementRespVO> settlementOrder(@Valid AppTradeOrderSettlementReqVO settlementReqVO) {
        return success(tradeOrderUpdateService.settlementOrder(getLoginUserId(), settlementReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "Create order")
    @PreAuthenticated
    public CommonResult<AppTradeOrderCreateRespVO> createOrder(@Valid @RequestBody AppTradeOrderCreateReqVO createReqVO) {
        TradeOrderDO order = tradeOrderUpdateService.createOrder(getLoginUserId(), createReqVO);
        return success(new AppTradeOrderCreateRespVO().setId(order.getId()).setPayOrderId(order.getPayOrderId()));
    }

    @PostMapping("/update-paid")
    @Operation(summary = "Update order to paid") // By pay-module Payment Service，Make callback，Visible PayNotifyJob
    public CommonResult<Boolean> updateOrderPaid(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        tradeOrderUpdateService.updateOrderPaid(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayOrderId());
        return success(true);
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get transaction order")
    @Parameter(name = "id", description = "Transaction order number")
    public CommonResult<AppTradeOrderDetailRespVO> getOrder(@RequestParam("id") Long id) {
        // Query order
        TradeOrderDO order = tradeOrderQueryService.getOrder(getLoginUserId(), id);
        if (order == null) {
            return success(null);
        }

        // Query order items
        List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(order.getId());
        // Query logistics company
        DeliveryExpressDO express = order.getLogisticsId() != null && order.getLogisticsId() > 0 ?
                deliveryExpressService.getDeliveryExpress(order.getLogisticsId()) : null;
        // Final combination
        return success(TradeOrderConvert.INSTANCE.convert02(order, orderItems, tradeOrderProperties, express));
    }

    @GetMapping("/get-express-track-list")
    @Operation(summary = "Get the logistics track of the transaction order")
    @Parameter(name = "id", description = "Transaction order number")
    public CommonResult<List<AppOrderExpressTrackRespDTO>> getOrderExpressTrackList(@RequestParam("id") Long id) {
        return success(TradeOrderConvert.INSTANCE.convertList02(
                tradeOrderQueryService.getExpressTrackList(id, getLoginUserId())));
    }

    @GetMapping("/page")
    @Operation(summary = "Get transaction order paging")
    public CommonResult<PageResult<AppTradeOrderPageItemRespVO>> getOrderPage(AppTradeOrderPageReqVO reqVO) {
        // Query order
        PageResult<TradeOrderDO> pageResult = tradeOrderQueryService.getOrderPage(getLoginUserId(), reqVO);
        // Query order items
        List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(
                convertSet(pageResult.getList(), TradeOrderDO::getId));
        // Final combination
        return success(TradeOrderConvert.INSTANCE.convertPage02(pageResult, orderItems));
    }

    @GetMapping("/get-count")
    @Operation(summary = "Get the number of transaction orders")
    public CommonResult<Map<String, Long>> getOrderCount() {
        Map<String, Long> orderCount = Maps.newLinkedHashMapWithExpectedSize(5);
        // All
        orderCount.put("allCount", tradeOrderQueryService.getOrderCount(getLoginUserId(), null, null));
        // Awaiting payment（Not paid）
        orderCount.put("unpaidCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                TradeOrderStatusEnum.UNPAID.getStatus(), null));
        // Waiting for delivery
        orderCount.put("undeliveredCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                TradeOrderStatusEnum.UNDELIVERED.getStatus(), null));
        // Waiting for delivery
        orderCount.put("deliveredCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                TradeOrderStatusEnum.DELIVERED.getStatus(), null));
        // Awaiting evaluation
        orderCount.put("uncommentedCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                TradeOrderStatusEnum.COMPLETED.getStatus(), false));
        // After-sales quantity
        orderCount.put("afterSaleCount", afterSaleService.getApplyingAfterSaleCount(getLoginUserId()));
        return success(orderCount);
    }

    @PutMapping("/receive")
    @Operation(summary = "Confirm transaction order receipt")
    @Parameter(name = "id", description = "Transaction order number")
    public CommonResult<Boolean> receiveOrder(@RequestParam("id") Long id) {
        tradeOrderUpdateService.receiveOrderByMember(getLoginUserId(), id);
        return success(true);
    }

    @DeleteMapping("/cancel")
    @Operation(summary = "Cancel transaction order")
    @Parameter(name = "id", description = "Transaction order number")
    public CommonResult<Boolean> cancelOrder(@RequestParam("id") Long id) {
        tradeOrderUpdateService.cancelOrderByMember(getLoginUserId(), id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete transaction order")
    @Parameter(name = "id", description = "Transaction order number")
    public CommonResult<Boolean> deleteOrder(@RequestParam("id") Long id) {
        tradeOrderUpdateService.deleteOrder(getLoginUserId(), id);
        return success(true);
    }

    // ========== Order Item ==========

    @GetMapping("/item/get")
    @Operation(summary = "Get transaction order items")
    @Parameter(name = "id", description = "Transaction order item number")
    public CommonResult<AppTradeOrderItemRespVO> getOrderItem(@RequestParam("id") Long id) {
        TradeOrderItemDO item = tradeOrderQueryService.getOrderItem(getLoginUserId(), id);
        return success(TradeOrderConvert.INSTANCE.convert03(item));
    }

    @PostMapping("/item/create-comment")
    @Operation(summary = "Evaluation of creating transaction order items")
    public CommonResult<Long> createOrderItemComment(@RequestBody AppTradeOrderItemCommentCreateReqVO createReqVO) {
        return success(tradeOrderUpdateService.createOrderItemCommentByMember(getLoginUserId(), createReqVO));
    }

}
