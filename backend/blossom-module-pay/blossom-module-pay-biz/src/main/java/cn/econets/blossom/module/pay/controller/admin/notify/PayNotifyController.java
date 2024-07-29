package cn.econets.blossom.module.pay.controller.admin.notify;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.pay.core.client.PayClient;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.module.pay.controller.admin.notify.vo.PayNotifyTaskDetailRespVO;
import cn.econets.blossom.module.pay.controller.admin.notify.vo.PayNotifyTaskPageReqVO;
import cn.econets.blossom.module.pay.controller.admin.notify.vo.PayNotifyTaskRespVO;
import cn.econets.blossom.module.pay.convert.notify.PayNotifyTaskConvert;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import cn.econets.blossom.module.pay.dal.dataobject.notify.PayNotifyTaskDO;
import cn.econets.blossom.module.pay.service.app.PayAppService;
import cn.econets.blossom.module.pay.service.channel.PayChannelService;
import cn.econets.blossom.module.pay.service.notify.PayNotifyService;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
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

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.CHANNEL_NOT_FOUND;

@Tag(name = "Management Backend - Callback notification")
@RestController
@RequestMapping("/pay/notify")
@Validated
@Slf4j
public class PayNotifyController {

    @Resource
    private PayOrderService orderService;
    @Resource
    private PayRefundService refundService;
    @Resource
    private PayNotifyService notifyService;
    @Resource
    private PayAppService appService;
    @Resource
    private PayChannelService channelService;

    @PostMapping(value = "/order/{channelId}")
    @Operation(summary = "Unification of payment channels【Payment】Callback")
    @PermitAll
    @OperateLog(enable = false) // Callback address，No need to record operation logs
    public String notifyOrder(@PathVariable("channelId") Long channelId,
                              @RequestParam(required = false) Map<String, String> params,
                              @RequestBody(required = false) String body) {
        log.info("[notifyOrder][channelId({}) Callback data({}/{})]", channelId, params, body);
        // 1. Check whether the payment channel exists
        PayClient payClient = channelService.getPayClient(channelId);
        if (payClient == null) {
            log.error("[notifyCallback][Channel Number({}) Cannot find the corresponding payment client]", channelId);
            throw exception(CHANNEL_NOT_FOUND);
        }

        // 2. Parsing notification data
        PayOrderRespDTO notify = payClient.parseOrderNotify(params, body);
        orderService.notifyOrder(channelId, notify);
        return "success";
    }

    @PostMapping(value = "/refund/{channelId}")
    @Operation(summary = "Unification of payment channels【Refund】Callback")
    @PermitAll
    @OperateLog(enable = false) // Callback address，No need to record operation logs
    public String notifyRefund(@PathVariable("channelId") Long channelId,
                              @RequestParam(required = false) Map<String, String> params,
                              @RequestBody(required = false) String body) {
        log.info("[notifyRefund][channelId({}) Callback data({}/{})]", channelId, params, body);
        // 1. Check whether the payment channel exists
        PayClient payClient = channelService.getPayClient(channelId);
        if (payClient == null) {
            log.error("[notifyCallback][Channel Number({}) Cannot find the corresponding payment client]", channelId);
            throw exception(CHANNEL_NOT_FOUND);
        }

        // 2. Parsing notification data
        PayRefundRespDTO notify = payClient.parseRefundNotify(params, body);
        refundService.notifyRefund(channelId, notify);
        return "success";
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get callback notification details")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:notify:query')")
    public CommonResult<PayNotifyTaskDetailRespVO> getNotifyTaskDetail(@RequestParam("id") Long id) {
        PayNotifyTaskDO task = notifyService.getNotifyTask(id);
        if (task == null) {
            return success(null);
        }
        // Splicing returns
        PayAppDO app = appService.getApp(task.getAppId());
        List<PayNotifyLogDO> logs = notifyService.getNotifyLogList(id);
        return success(PayNotifyTaskConvert.INSTANCE.convert(task, app, logs));
    }

    @GetMapping("/page")
    @Operation(summary = "Get callback notification paging")
    @PreAuthorize("@ss.hasPermission('pay:notify:query')")
    public CommonResult<PageResult<PayNotifyTaskRespVO>> getNotifyTaskPage(@Valid PayNotifyTaskPageReqVO pageVO) {
        PageResult<PayNotifyTaskDO> pageResult = notifyService.getNotifyTaskPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }
        // Splicing returns
        Map<Long, PayAppDO> appMap = appService.getAppMap(convertList(pageResult.getList(), PayNotifyTaskDO::getAppId));
        return success(PayNotifyTaskConvert.INSTANCE.convertPage(pageResult, appMap));
    }

}
