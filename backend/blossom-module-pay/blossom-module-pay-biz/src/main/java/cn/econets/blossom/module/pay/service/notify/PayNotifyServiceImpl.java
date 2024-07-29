package cn.econets.blossom.module.pay.service.notify;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.econets.blossom.module.pay.api.notify.dto.PayRefundNotifyReqDTO;
import cn.econets.blossom.module.pay.api.notify.dto.PayTransferNotifyReqDTO;
import cn.econets.blossom.module.pay.controller.admin.notify.vo.PayNotifyTaskPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import cn.econets.blossom.module.pay.dal.dataobject.notify.PayNotifyTaskDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.dal.dataobject.transfer.PayTransferDO;
import cn.econets.blossom.module.pay.dal.mysql.notify.PayNotifyLogMapper;
import cn.econets.blossom.module.pay.dal.mysql.notify.PayNotifyTaskMapper;
import cn.econets.blossom.module.pay.dal.redis.notify.PayNotifyLockRedisDAO;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyStatusEnum;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyTypeEnum;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
import cn.econets.blossom.module.pay.service.transfer.PayTransferService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.addTime;
import static cn.econets.blossom.module.pay.framework.job.config.PayJobConfiguration.NOTIFY_THREAD_POOL_TASK_EXECUTOR;

/**
 * Payment Notice Core Service Implementation class
 *
 *
 */
@Service
@Valid
@Slf4j
public class PayNotifyServiceImpl implements PayNotifyService {

    /**
     * Notification timeout，Unit：seconds
     */
    public static final int NOTIFY_TIMEOUT = 120;
    /**
     * {@link #NOTIFY_TIMEOUT} milliseconds
     */
    public static final long NOTIFY_TIMEOUT_MILLIS = 120 * DateUtils.SECOND_MILLIS;

    @Resource
    @Lazy // Circular dependency，Avoid errors
    private PayOrderService orderService;
    @Resource
    @Lazy // Circular dependency，Avoid errors
    private PayRefundService refundService;
    @Resource
    @Lazy // Circular dependency，Avoid errors
    private PayTransferService transferService;

    @Resource
    private PayNotifyTaskMapper notifyTaskMapper;
    @Resource
    private PayNotifyLogMapper notifyLogMapper;

    @Resource(name = NOTIFY_THREAD_POOL_TASK_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private PayNotifyLockRedisDAO notifyLockCoreRedisDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPayNotifyTask(Integer type, Long dataId) {
        PayNotifyTaskDO task = new PayNotifyTaskDO().setType(type).setDataId(dataId);
        task.setStatus(PayNotifyStatusEnum.WAITING.getStatus()).setNextNotifyTime(LocalDateTime.now())
                .setNotifyTimes(0).setMaxNotifyTimes(PayNotifyTaskDO.NOTIFY_FREQUENCY.length + 1);
        // Supplement appId + notifyUrl Field
        if (Objects.equals(task.getType(), PayNotifyTypeEnum.ORDER.getType())) {
            PayOrderDO order = orderService.getOrder(task.getDataId()); // Do not perform non-empty judgment，If there is a problem, just make an exception
            task.setAppId(order.getAppId()).
                    setMerchantOrderId(order.getMerchantOrderId()).setNotifyUrl(order.getNotifyUrl());
        } else if (Objects.equals(task.getType(), PayNotifyTypeEnum.REFUND.getType())) {
            PayRefundDO refundDO = refundService.getRefund(task.getDataId());
            task.setAppId(refundDO.getAppId())
                    .setMerchantOrderId(refundDO.getMerchantOrderId()).setNotifyUrl(refundDO.getNotifyUrl());
        } else if (Objects.equals(task.getType(), PayNotifyTypeEnum.TRANSFER.getType())) {
            PayTransferDO transfer = transferService.getTransfer(task.getDataId());
            task.setAppId(transfer.getAppId()).setMerchantTransferId(transfer.getMerchantTransferId())
                    .setNotifyUrl(transfer.getNotifyUrl());
        }

        // Execute insert
        notifyTaskMapper.insert(task);

        // Must be after transaction is committed，Initiating a task，Otherwise PayNotifyTaskDO Not yet in stock，Call back the connected service in advance
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                executeNotify(task);
            }
        });
    }

    @Override
    public int executeNotify() throws InterruptedException {
        // Get tasks that need to be notified
        List<PayNotifyTaskDO> tasks = notifyTaskMapper.selectListByNotify();
        if (CollUtil.isEmpty(tasks)) {
            return 0;
        }

        // Traverse，Notify one by one
        CountDownLatch latch = new CountDownLatch(tasks.size());
        tasks.forEach(task -> threadPoolTaskExecutor.execute(() -> {
            try {
                executeNotify(task);
            } finally {
                latch.countDown();
            }
        }));
        // Waiting to complete
        awaitExecuteNotify(latch);
        // Return the number of completed tasks（Success + Failed)
        return tasks.size();
    }

    /**
     * Waiting for all payment notifications to be completed
     * Every 1 The number of remaining tasks will be printed once per second
     *
     * @param latch Latch
     * @throws InterruptedException If interrupted
     */
    private void awaitExecuteNotify(CountDownLatch latch) throws InterruptedException {
        long size = latch.getCount();
        for (int i = 0; i < NOTIFY_TIMEOUT; i++) {
            if (latch.await(1L, TimeUnit.SECONDS)) {
                return;
            }
            log.info("[awaitExecuteNotify][Task processing， Total number of tasks({}) Number of remaining tasks({})]", size, latch.getCount());
        }
        log.error("[awaitExecuteNotify][Task not completed，Total number of tasks({}) Number of remaining tasks({})]", size, latch.getCount());
    }

    /**
     * Synchronize single payment notification
     *
     * @param task Notification task
     */
    public void executeNotify(PayNotifyTaskDO task) {
        // Distributed lock，Avoid concurrency issues
        notifyLockCoreRedisDAO.lock(task.getId(), NOTIFY_TIMEOUT_MILLIS, () -> {
            // Verification，Has the current task been notified?
            // Although distributed locking has been implemented，But the notification conditions may be met at the same time，Then go get the lock。At this time，After the first execution is completed，The second one can still get the lock，Then it will be executed again。
            // Therefore，Here we pass the first notifyTimes Judged by whether the number of notifications matches
            PayNotifyTaskDO dbTask = notifyTaskMapper.selectById(task.getId());
            if (ObjectUtil.notEqual(task.getNotifyTimes(), dbTask.getNotifyTimes())) {
                log.warn("[executeNotifySync][task({}) Task was ignored，The reason is that its notification is not the first ({}) times，Maybe it's due to concurrent execution]",
                        JsonUtils.toJsonString(task), dbTask.getNotifyTimes());
                return;
            }

            // Execution Notice
            getSelf().executeNotify0(dbTask);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void executeNotify0(PayNotifyTaskDO task) {
        // Initiate callback
        CommonResult<?> invokeResult = null;
        Throwable invokeException = null;
        try {
            invokeResult = executeNotifyInvoke(task);
        } catch (Throwable e) {
            invokeException = e;
        }

        // Processing results
        Integer newStatus = processNotifyResult(task, invokeResult, invokeException);

        // Record PayNotifyLog Log
        String response = invokeException != null ? ExceptionUtil.getRootCauseMessage(invokeException) :
                JsonUtils.toJsonString(invokeResult);
        notifyLogMapper.insert(PayNotifyLogDO.builder().taskId(task.getId())
                .notifyTimes(task.getNotifyTimes() + 1).status(newStatus).response(response).build());
    }

    /**
     * Execute a single payment task HTTP Call
     *
     * @param task Notification task
     * @return HTTP Response
     */
    private CommonResult<?> executeNotifyInvoke(PayNotifyTaskDO task) {
        // Splicing body Parameters
        Object request;
        if (Objects.equals(task.getType(), PayNotifyTypeEnum.ORDER.getType())) {
            request = PayOrderNotifyReqDTO.builder().merchantOrderId(task.getMerchantOrderId())
                    .payOrderId(task.getDataId()).build();
        } else if (Objects.equals(task.getType(), PayNotifyTypeEnum.REFUND.getType())) {
            request = PayRefundNotifyReqDTO.builder().merchantOrderId(task.getMerchantOrderId())
                    .payRefundId(task.getDataId()).build();
        } else if (Objects.equals(task.getType(), PayNotifyTypeEnum.TRANSFER.getType())) {
            request = new PayTransferNotifyReqDTO().setMerchantTransferId(task.getMerchantTransferId())
                    .setPayTransferId(task.getDataId());
        } else {
            throw new RuntimeException("Unknown notification task type：" + JsonUtils.toJsonString(task));
        }
        // Splicing header Parameters
        Map<String, String> headers = new HashMap<>();
        TenantUtils.addTenantHeader(headers, task.getTenantId());

        // Initiate request
        try (HttpResponse response = HttpUtil.createPost(task.getNotifyUrl())
                .body(JsonUtils.toJsonString(request)).addHeaders(headers)
                .timeout((int) NOTIFY_TIMEOUT_MILLIS).execute()) {
            // Analysis results
            return JsonUtils.parseObject(response.body(), CommonResult.class);
        }
    }

    /**
     * Process and update notification results
     *
     * @param task Notification task
     * @param invokeResult Notify result
     * @param invokeException Notification exception
     * @return Status of the final task
     */
    @VisibleForTesting
    Integer processNotifyResult(PayNotifyTaskDO task, CommonResult<?> invokeResult, Throwable invokeException) {
        // Set up general updates PayNotifyTaskDO Fields
        PayNotifyTaskDO updateTask = new PayNotifyTaskDO()
                .setId(task.getId())
                .setLastExecuteTime(LocalDateTime.now())
                .setNotifyTimes(task.getNotifyTimes() + 1);

        // Situation 1：Call successful
        if (invokeResult != null && invokeResult.isSuccess()) {
            updateTask.setStatus(PayNotifyStatusEnum.SUCCESS.getStatus());
            notifyTaskMapper.updateById(updateTask);
            return updateTask.getStatus();
        }

        // Case 2：Call failed、Call exception
        // 2.1 Exceeded the maximum number of callbacks
        if (updateTask.getNotifyTimes() >= PayNotifyTaskDO.NOTIFY_FREQUENCY.length) {
            updateTask.setStatus(PayNotifyStatusEnum.FAILURE.getStatus());
            notifyTaskMapper.updateById(updateTask);
            return updateTask.getStatus();
        }
        // 2.2 The maximum number of callbacks has not been exceeded
        updateTask.setNextNotifyTime(addTime(Duration.ofSeconds(PayNotifyTaskDO.NOTIFY_FREQUENCY[updateTask.getNotifyTimes()])));
        updateTask.setStatus(invokeException != null ? PayNotifyStatusEnum.REQUEST_FAILURE.getStatus()
                : PayNotifyStatusEnum.REQUEST_SUCCESS.getStatus());
        notifyTaskMapper.updateById(updateTask);
        return updateTask.getStatus();
    }

    @Override
    public PayNotifyTaskDO getNotifyTask(Long id) {
        return notifyTaskMapper.selectById(id);
    }

    @Override
    public PageResult<PayNotifyTaskDO> getNotifyTaskPage(PayNotifyTaskPageReqVO pageReqVO) {
        return notifyTaskMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PayNotifyLogDO> getNotifyLogList(Long taskId) {
        return notifyLogMapper.selectListByTaskId(taskId);
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private PayNotifyServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
