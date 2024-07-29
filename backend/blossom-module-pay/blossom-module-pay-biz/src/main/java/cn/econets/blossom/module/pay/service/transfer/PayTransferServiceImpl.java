package cn.econets.blossom.module.pay.service.transfer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.pay.core.client.PayClient;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferStatusRespEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.module.pay.api.transfer.dto.PayTransferCreateReqDTO;
import cn.econets.blossom.module.pay.controller.admin.transfer.vo.PayTransferCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.transfer.vo.PayTransferPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.dal.dataobject.transfer.PayTransferDO;
import cn.econets.blossom.module.pay.dal.mysql.transfer.PayTransferMapper;
import cn.econets.blossom.module.pay.dal.redis.no.PayNoRedisDAO;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyTypeEnum;
import cn.econets.blossom.module.pay.enums.transfer.PayTransferStatusEnum;
import cn.econets.blossom.module.pay.service.app.PayAppService;
import cn.econets.blossom.module.pay.service.channel.PayChannelService;
import cn.econets.blossom.module.pay.service.notify.PayNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.pay.convert.transfer.PayTransferConvert.INSTANCE;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.pay.enums.transfer.PayTransferStatusEnum.*;

// TODO ：Wait until it is fully realized，Single test write；

/**
 * Transfer Service Implementation class
 *
 *
 */
@Service
@Slf4j
public class PayTransferServiceImpl implements PayTransferService {

    private static final String TRANSFER_NO_PREFIX = "T";

    @Resource
    private PayTransferMapper transferMapper;
    @Resource
    private PayAppService appService;
    @Resource
    private PayChannelService channelService;
    @Resource
    private PayNotifyService notifyService;
    @Resource
    private PayNoRedisDAO noRedisDAO;
    @Resource
    private Validator validator;

    @Override
    public PayTransferDO createTransfer(PayTransferCreateReqVO reqVO, String userIp) {
        // 1. Verify parameters
        reqVO.validate(validator);

        // 2. Create a transfer order，Initiate transfer
        PayTransferCreateReqDTO req = INSTANCE.convert(reqVO).setUserIp(userIp);
        Long transferId = createTransfer(req);

        // 3. Return transfer slip
        return getTransfer(transferId);
    }

    @Override
    public Long createTransfer(PayTransferCreateReqDTO reqDTO) {
        // 1.1 Verification App
        PayAppDO payApp = appService.validPayApp(reqDTO.getAppId());
        // 1.2 Check whether the payment channel is valid
        PayChannelDO channel = channelService.validPayChannel(reqDTO.getAppId(), reqDTO.getChannelCode());
        PayClient client = channelService.getPayClient(channel.getId());
        if (client == null) {
            log.error("[createTransfer][Channel Number({}) Cannot find the corresponding payment client]", channel.getId());
            throw exception(CHANNEL_NOT_FOUND);
        }
        // 1.3 The verification transfer order has initiated a transfer。
        PayTransferDO transfer = validateTransferCanCreate(reqDTO);

        if (transfer == null) {
            // 2.There is no creation transfer order. Otherwise, the same is allowed no Initiate transfer again
            String no = noRedisDAO.generate(TRANSFER_NO_PREFIX);
            transfer = INSTANCE.convert(reqDTO)
                    .setChannelId(channel.getId())
                    .setNo(no).setStatus(WAITING.getStatus())
                    .setNotifyUrl(payApp.getTransferNotifyUrl());
            transferMapper.insert(transfer);
        }
        try {
            // 3. Call the third-party channel to initiate a transfer
            PayTransferUnifiedReqDTO transferUnifiedReq = INSTANCE.convert2(transfer)
                    .setOutTransferNo(transfer.getNo());
            PayTransferRespDTO unifiedTransferResp = client.unifiedTransfer(transferUnifiedReq);
            // 4. Notify transfer result
            getSelf().notifyTransfer(channel, unifiedTransferResp);
        } catch (Throwable e) {
            // Note that only exceptions are printed here，No throw。
            // The reason is：Although an exception occurred when calling the payment channel to transfer money（Network request timed out），The actual transfer was successful。This result，Subsequent transfer polling can be obtained。
            // Or use the same no Initiate transfer request again
            log.error("[createTransfer][Transfer id({}) requestDTO({}) Exception occurred]", transfer.getId(), reqDTO, e);
        }

        return transfer.getId();
    }

    private PayTransferDO validateTransferCanCreate(PayTransferCreateReqDTO dto) {
        PayTransferDO transfer = transferMapper.selectByAppIdAndMerchantTransferId(dto.getAppId(), dto.getMerchantTransferId());
        if (transfer != null) {
            // Already exists,and the status is not waiting。Indicates that the channel transfer has been called and the result has been returned.
            if (!PayTransferStatusEnum.isWaiting(transfer.getStatus())) {
                throw exception(PAY_MERCHANT_TRANSFER_EXISTS);
            }
            if (ObjectUtil.notEqual(dto.getPrice(), transfer.getPrice())) {
                throw exception(PAY_SAME_MERCHANT_TRANSFER_PRICE_NOT_MATCH);
            }
            if (ObjectUtil.notEqual(dto.getType(), transfer.getType())) {
                throw exception(PAY_SAME_MERCHANT_TRANSFER_TYPE_NOT_MATCH);
            }
        }
        // If the status is waiting。Not sure whether the channel transfer was initiated successfully。 Allow the same no Initiate transfer again，The channel will ensure idempotence
        return transfer;
    }

    @Transactional(rollbackFor = Exception.class)
    // Attention，If the method is called from within a method，Need to pass getSelf().notifyTransfer(channel, notify) Call，Otherwise the transaction will not take effect
    public void notifyTransfer(PayChannelDO channel, PayTransferRespDTO notify) {
        // Callback for successful transfer
        if (PayTransferStatusRespEnum.isSuccess(notify.getStatus())) {
            notifyTransferSuccess(channel, notify);
        }
        // Callback for transfer closure
        if (PayTransferStatusRespEnum.isClosed(notify.getStatus())) {
            notifyTransferClosed(channel, notify);
        }
        // Callback during transfer processing
        if (PayTransferStatusRespEnum.isInProgress(notify.getStatus())) {
            notifyTransferInProgress(channel, notify);
        }
        // WAITING Status does not require processing
    }

    private void notifyTransferInProgress(PayChannelDO channel, PayTransferRespDTO notify) {
        // 1.Verification
        PayTransferDO transfer = transferMapper.selectByNo(notify.getOutTransferNo());
        if (transfer == null) {
            throw exception(PAY_TRANSFER_NOT_FOUND);
        }
        if (isInProgress(transfer.getStatus())) { // If the transfer is already in progress，Return directly，No need to update repeatedly
            return;
        }
        if (!isWaiting(transfer.getStatus())) {
            throw exception(PAY_TRANSFER_STATUS_IS_NOT_WAITING);
        }
        // 2.Update
        int updateCounts = transferMapper.updateByIdAndStatus(transfer.getId(),
                CollUtil.newArrayList(WAITING.getStatus()),
                new PayTransferDO().setStatus(IN_PROGRESS.getStatus()));
        if (updateCounts == 0) {
            throw exception(PAY_TRANSFER_STATUS_IS_NOT_WAITING);
        }
        log.info("[notifyTransferInProgress][transfer({}) Update to transfer in progress]", transfer.getId());

        // 3. Insert transfer notification record
        notifyService.createPayNotifyTask(PayNotifyTypeEnum.TRANSFER.getType(),
                transfer.getId());
    }


    private void notifyTransferSuccess(PayChannelDO channel, PayTransferRespDTO notify) {
        // 1.Verification
        PayTransferDO transfer = transferMapper.selectByNo(notify.getOutTransferNo());
        if (transfer == null) {
            throw exception(PAY_TRANSFER_NOT_FOUND);
        }
        if (isSuccess(transfer.getStatus())) { // If successful，Return directly，No need to update repeatedly
            return;
        }
        if (!isPendingStatus(transfer.getStatus())) {
            throw exception(PAY_TRANSFER_STATUS_IS_NOT_PENDING);
        }
        // 2.Update
        int updateCounts = transferMapper.updateByIdAndStatus(transfer.getId(),
                CollUtil.newArrayList(WAITING.getStatus(), IN_PROGRESS.getStatus()),
                new PayTransferDO().setStatus(SUCCESS.getStatus()).setSuccessTime(notify.getSuccessTime())
                        .setChannelTransferNo(notify.getChannelTransferNo())
                        .setChannelId(channel.getId()).setChannelCode(channel.getCode())
                        .setChannelNotifyData(JsonUtils.toJsonString(notify)));
        if (updateCounts == 0) {
            throw exception(PAY_TRANSFER_STATUS_IS_NOT_PENDING);
        }
        log.info("[updateTransferSuccess][transfer({}) Updated to transferred]", transfer.getId());

        // 3. Insert transfer notification record
        notifyService.createPayNotifyTask(PayNotifyTypeEnum.TRANSFER.getType(),
                transfer.getId());
    }

    private void notifyTransferClosed(PayChannelDO channel, PayTransferRespDTO notify) {
        // 1.Verification
        PayTransferDO transfer = transferMapper.selectByNo(notify.getOutTransferNo());
        if (transfer == null) {
            throw exception(PAY_TRANSFER_NOT_FOUND);
        }
        if (isClosed(transfer.getStatus())) { // If it is already closed，Return directly，No need to update repeatedly
            log.info("[updateTransferClosed][transfer({}) Already closed，No update required]", transfer.getId());
            return;
        }
        if (!isPendingStatus(transfer.getStatus())) {
            throw exception(PAY_TRANSFER_STATUS_IS_NOT_PENDING);
        }

        // 2.Update
        int updateCount = transferMapper.updateByIdAndStatus(transfer.getId(),
                CollUtil.newArrayList(WAITING.getStatus(), IN_PROGRESS.getStatus()),
                new PayTransferDO().setStatus(CLOSED.getStatus()).setChannelId(channel.getId())
                        .setChannelCode(channel.getCode()).setChannelTransferNo(notify.getChannelTransferNo())
                        .setChannelErrorCode(notify.getChannelErrorCode()).setChannelErrorMsg(notify.getChannelErrorMsg())
                        .setChannelNotifyData(JsonUtils.toJsonString(notify)));
        if (updateCount == 0) {
            throw exception(PAY_TRANSFER_STATUS_IS_NOT_PENDING);
        }
        log.info("[updateTransferClosed][transfer({}) Update to closed state]", transfer.getId());

        // 3. Insert transfer notification record
        notifyService.createPayNotifyTask(PayNotifyTypeEnum.TRANSFER.getType(),
                transfer.getId());

    }

    @Override
    public PayTransferDO getTransfer(Long id) {
        return transferMapper.selectById(id);
    }

    @Override
    public PageResult<PayTransferDO> getTransferPage(PayTransferPageReqVO pageReqVO) {
        return transferMapper.selectPage(pageReqVO);
    }

    @Override
    public int syncTransfer() {
        List<PayTransferDO> list = transferMapper.selectListByStatus(WAITING.getStatus());
        if (CollUtil.isEmpty(list)) {
            return 0;
        }
        int count = 0;
        for (PayTransferDO transfer : list) {
            count += syncTransfer(transfer) ? 1 : 0;
        }
        return count;
    }

    private boolean syncTransfer(PayTransferDO transfer) {
        try {
            // 1. Query transfer order information
            PayClient payClient = channelService.getPayClient(transfer.getChannelId());
            if (payClient == null) {
                log.error("[syncTransfer][Channel Number({}) Cannot find the corresponding payment client]", transfer.getChannelId());
                return false;
            }
            PayTransferRespDTO resp = payClient.getTransfer(transfer.getNo(),
                    PayTransferTypeEnum.typeOf(transfer.getType()));

            // 2. Call back the transfer result
            notifyTransfer(transfer.getChannelId(), resp);
            return true;
        } catch (Throwable ex) {
            log.error("[syncTransfer][transfer({}) Synchronous transfer order status is abnormal]", transfer.getId(), ex);
            return false;
        }
    }

    private void notifyTransfer(Long channelId, PayTransferRespDTO notify) {
        // Check whether the channel is valid
        PayChannelDO channel = channelService.validPayChannel(channelId);
        // Notify the corresponding business of the transfer result
        TenantUtils.execute(channel.getTenantId(), () -> getSelf().notifyTransfer(channel, notify));
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private PayTransferServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }
}
