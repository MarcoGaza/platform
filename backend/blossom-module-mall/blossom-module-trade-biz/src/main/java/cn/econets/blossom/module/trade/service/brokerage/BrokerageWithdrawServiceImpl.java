package cn.econets.blossom.module.trade.service.brokerage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.number.MoneyUtils;
import cn.econets.blossom.module.system.api.notify.NotifyMessageSendApi;
import cn.econets.blossom.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw.AppBrokerageWithdrawCreateReqVO;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageWithdrawConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageWithdrawDO;
import cn.econets.blossom.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.econets.blossom.module.trade.dal.mysql.brokerage.BrokerageWithdrawMapper;
import cn.econets.blossom.module.trade.enums.MessageTemplateConstants;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageWithdrawSummaryRespBO;
import cn.econets.blossom.module.trade.service.config.TradeConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.*;

/**
 * Commission withdrawal Service Implementation class
 *
 */
@Service
@Validated
public class BrokerageWithdrawServiceImpl implements BrokerageWithdrawService {

    @Resource
    private BrokerageWithdrawMapper brokerageWithdrawMapper;

    @Resource
    private BrokerageRecordService brokerageRecordService;
    @Resource
    private TradeConfigService tradeConfigService;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Resource
    private Validator validator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditBrokerageWithdraw(Integer id, BrokerageWithdrawStatusEnum status, String auditReason) {
        // 1.1 Check existence
        BrokerageWithdrawDO withdraw = validateBrokerageWithdrawExists(id);
        // 1.2 The verification status is under review
        if (ObjectUtil.notEqual(BrokerageWithdrawStatusEnum.AUDITING.getStatus(), withdraw.getStatus())) {
            throw exception(BROKERAGE_WITHDRAW_STATUS_NOT_AUDITING);
        }

        // 2. Update
        int rows = brokerageWithdrawMapper.updateByIdAndStatus(id, BrokerageWithdrawStatusEnum.AUDITING.getStatus(),
                new BrokerageWithdrawDO().setStatus(status.getStatus()).setAuditReason(auditReason).setAuditTime(LocalDateTime.now()));
        if (rows == 0) {
            throw exception(BROKERAGE_WITHDRAW_STATUS_NOT_AUDITING);
        }

        String templateCode;
        if (BrokerageWithdrawStatusEnum.AUDIT_SUCCESS.equals(status)) {
            templateCode = MessageTemplateConstants.BROKERAGE_WITHDRAW_AUDIT_APPROVE;
            // 3.1 Commission transfer balance upon passing
            if (BrokerageWithdrawTypeEnum.WALLET.getType().equals(withdraw.getType())) {
                // todo Crazy：
            }
            // TODO Crazy：Call transfer interface
        } else if (BrokerageWithdrawStatusEnum.AUDIT_FAIL.equals(status)) {
            templateCode = MessageTemplateConstants.BROKERAGE_WITHDRAW_AUDIT_REJECT;
            // 3.2 User commissions must be refunded upon rejection
            brokerageRecordService.addBrokerage(withdraw.getUserId(), BrokerageRecordBizTypeEnum.WITHDRAW_REJECT,
                    String.valueOf(withdraw.getId()), withdraw.getPrice(), BrokerageRecordBizTypeEnum.WITHDRAW_REJECT.getTitle());
        } else {
            throw new IllegalArgumentException("Unsupported withdrawal status：" + status);
        }

        // 4. Notify user
        Map<String, Object> templateParams = MapUtil.<String, Object>builder()
                .put("createTime", LocalDateTimeUtil.formatNormal(withdraw.getCreateTime()))
                .put("price", MoneyUtils.fenToYuanStr(withdraw.getPrice()))
                .put("reason", withdraw.getAuditReason())
                .build();
        notifyMessageSendApi.sendSingleMessageToMember(new NotifySendSingleToUserReqDTO()
                .setUserId(withdraw.getUserId()).setTemplateCode(templateCode).setTemplateParams(templateParams));
    }

    private BrokerageWithdrawDO validateBrokerageWithdrawExists(Integer id) {
        BrokerageWithdrawDO withdraw = brokerageWithdrawMapper.selectById(id);
        if (withdraw == null) {
            throw exception(BROKERAGE_WITHDRAW_NOT_EXISTS);
        }
        return withdraw;
    }

    @Override
    public BrokerageWithdrawDO getBrokerageWithdraw(Integer id) {
        return brokerageWithdrawMapper.selectById(id);
    }

    @Override
    public PageResult<BrokerageWithdrawDO> getBrokerageWithdrawPage(BrokerageWithdrawPageReqVO pageReqVO) {
        return brokerageWithdrawMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBrokerageWithdraw(Long userId, AppBrokerageWithdrawCreateReqVO createReqVO) {
        // 1.1 Verify withdrawal amount
        TradeConfigDO tradeConfig = validateWithdrawPrice(createReqVO.getPrice());
        // 1.2 Verify withdrawal parameters
        createReqVO.validate(validator);

        // 2.1 Calculate handling fee
        Integer feePrice = calculateFeePrice(createReqVO.getPrice(), tradeConfig.getBrokerageWithdrawFeePercent());
        // 2.2 Create commission withdrawal record
        BrokerageWithdrawDO withdraw = BrokerageWithdrawConvert.INSTANCE.convert(createReqVO, userId, feePrice);
        brokerageWithdrawMapper.insert(withdraw);

        // 3. Create user commission record
        // Attention，Is the commission sufficient?，reduceBrokerage Already verified
        brokerageRecordService.reduceBrokerage(userId, BrokerageRecordBizTypeEnum.WITHDRAW, String.valueOf(withdraw.getId()),
                createReqVO.getPrice(), BrokerageRecordBizTypeEnum.WITHDRAW.getTitle());
        return withdraw.getId();
    }

    @Override
    public List<BrokerageWithdrawSummaryRespBO> getWithdrawSummaryListByUserId(Collection<Long> userIds,
                                                                               BrokerageWithdrawStatusEnum status) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return brokerageWithdrawMapper.selectCountAndSumPriceByUserIdAndStatus(userIds, status.getStatus());
    }

    /**
     * Calculate withdrawal fee
     *
     * @param withdrawPrice Withdrawal amount
     * @param percent       Percentage of handling fee
     * @return Withdrawal fee
     */
    Integer calculateFeePrice(Integer withdrawPrice, Integer percent) {
        Integer feePrice = 0;
        if (percent != null && percent > 0) {
            feePrice = MoneyUtils.calculateRatePrice(withdrawPrice, Double.valueOf(percent));
        }
        return feePrice;
    }

    /**
     * Verify withdrawal amount requirements
     *
     * @param withdrawPrice Withdrawal amount
     * @return Distribution Configuration
     */
    TradeConfigDO validateWithdrawPrice(Integer withdrawPrice) {
        TradeConfigDO tradeConfig = tradeConfigService.getTradeConfig();
        if (tradeConfig.getBrokerageWithdrawMinPrice() != null && withdrawPrice < tradeConfig.getBrokerageWithdrawMinPrice()) {
            throw exception(BROKERAGE_WITHDRAW_MIN_PRICE, MoneyUtils.fenToYuanStr(tradeConfig.getBrokerageWithdrawMinPrice()));
        }
        return tradeConfig;
    }
}
