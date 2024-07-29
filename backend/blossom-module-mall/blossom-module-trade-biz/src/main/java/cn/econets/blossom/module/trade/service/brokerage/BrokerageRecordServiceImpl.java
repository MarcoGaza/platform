package cn.econets.blossom.module.trade.service.brokerage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.*;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.number.MoneyUtils;
import cn.econets.blossom.framework.mybatis.core.util.MyBatisUtils;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.record.BrokerageRecordPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.record.AppBrokerageProductPriceRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankByPriceRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankPageReqVO;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageRecordConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageRecordDO;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import cn.econets.blossom.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.econets.blossom.module.trade.dal.mysql.brokerage.BrokerageRecordMapper;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordStatusEnum;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageAddReqBO;
import cn.econets.blossom.module.trade.service.brokerage.bo.UserBrokerageSummaryRespBO;
import cn.econets.blossom.module.trade.service.config.TradeConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.getMaxValue;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.getMinValue;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.BROKERAGE_WITHDRAW_USER_BALANCE_NOT_ENOUGH;

/**
 * Commission Record Service Implementation class
 *
 */
@Slf4j
@Service
@Validated
public class BrokerageRecordServiceImpl implements BrokerageRecordService {

    @Resource
    private BrokerageRecordMapper brokerageRecordMapper;
    @Resource
    private TradeConfigService tradeConfigService;
    @Resource
    private BrokerageUserService brokerageUserService;

    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;

    @Override
    public BrokerageRecordDO getBrokerageRecord(Integer id) {
        return brokerageRecordMapper.selectById(id);
    }

    @Override
    public PageResult<BrokerageRecordDO> getBrokerageRecordPage(BrokerageRecordPageReqVO pageReqVO) {
        return brokerageRecordMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBrokerage(Long userId, BrokerageRecordBizTypeEnum bizType, List<BrokerageAddReqBO> list) {
        TradeConfigDO memberConfig = tradeConfigService.getTradeConfig();
        // 0 Distribution function is not enabled
        if (memberConfig == null || !BooleanUtil.isTrue(memberConfig.getBrokerageEnabled())) {
            log.warn("[addBrokerage][Failed to increase commission：brokerageEnabled Not configured，userId({})", userId);
            return;
        }

        // 1.1 Obtain first-level promoter
        BrokerageUserDO firstUser = brokerageUserService.getBindBrokerageUser(userId);
        if (firstUser == null || !BooleanUtil.isTrue(firstUser.getBrokerageEnabled())) {
            return;
        }
        // 1.2 Calculate the first-level commission
        addBrokerage(firstUser, list, memberConfig.getBrokerageFrozenDays(), memberConfig.getBrokerageFirstPercent(),
                bizType, 1);

        // 2.1 Obtain a second-level promoter
        if (firstUser.getBindUserId() == null) {
            return;
        }
        BrokerageUserDO secondUser = brokerageUserService.getBrokerageUser(firstUser.getBindUserId());
        if (secondUser == null || !BooleanUtil.isTrue(secondUser.getBrokerageEnabled())) {
            return;
        }
        // 2.2 Calculate the secondary commission
        addBrokerage(secondUser, list, memberConfig.getBrokerageFrozenDays(), memberConfig.getBrokerageSecondPercent(),
                bizType, 2);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelBrokerage(Long userId, BrokerageRecordBizTypeEnum bizType, String bizId) {
        BrokerageRecordDO record = brokerageRecordMapper.selectByBizTypeAndBizIdAndUserId(bizType.getType(), bizId, userId);
        if (record == null) {
            log.error("[cancelBrokerage][userId({})][bizId({}) Update to invalid failed：The record does not exist]", userId, bizId);
            return;
        }

        // 1. Update commission records to expire
        BrokerageRecordDO updateObj = new BrokerageRecordDO().setStatus(BrokerageRecordStatusEnum.CANCEL.getStatus());
        int updateRows = brokerageRecordMapper.updateByIdAndStatus(record.getId(), record.getStatus(), updateObj);
        if (updateRows == 0) {
            log.error("[cancelBrokerage][record({}) Update to invalid failed]", record.getId());
            return;
        }

        // 2. Update user's commission
        if (BrokerageRecordStatusEnum.WAIT_SETTLEMENT.getStatus().equals(record.getStatus())) {
            brokerageUserService.updateUserFrozenPrice(userId, -record.getPrice());
        } else if (BrokerageRecordStatusEnum.SETTLEMENT.getStatus().equals(record.getStatus())) {
            brokerageUserService.updateUserPrice(userId, -record.getPrice());
        }
    }

    /**
     * Calculate commission
     *
     * @param basePrice  Commission base
     * @param percent    Commission ratio
     * @param fixedPrice Fixed Commission
     * @return Commission
     */
    int calculatePrice(Integer basePrice, Integer percent, Integer fixedPrice) {
        // 1. Prefer fixed commission
        if (fixedPrice != null && fixedPrice > 0) {
            return ObjectUtil.defaultIfNull(fixedPrice, 0);
        }
        // 2. Calculate commission based on proportion
        if (basePrice != null && basePrice > 0 && percent != null && percent > 0) {
            return MoneyUtils.calculateRatePriceFloor(basePrice, Double.valueOf(percent));
        }
        return 0;
    }

    /**
     * Increase user commission
     *
     * @param user                User
     * @param list                Commission increase parameter list
     * @param brokerageFrozenDays Freeze days
     * @param brokeragePercent    Commission ratio
     * @param bizType             Business Type
     * @param sourceUserLevel     Source user level
     */
    private void addBrokerage(BrokerageUserDO user, List<BrokerageAddReqBO> list, Integer brokerageFrozenDays,
                              Integer brokeragePercent, BrokerageRecordBizTypeEnum bizType, Integer sourceUserLevel) {
        // 1.1 Processing freeze time
        LocalDateTime unfreezeTime = null;
        if (brokerageFrozenDays != null && brokerageFrozenDays > 0) {
            unfreezeTime = LocalDateTime.now().plusDays(brokerageFrozenDays);
        }
        // 1.2 Calculate commission
        int totalBrokerage = 0;
        List<BrokerageRecordDO> records = new ArrayList<>();
        for (BrokerageAddReqBO item : list) {
            // Calculate amount
            Integer fixedPrice;
            if (Objects.equals(sourceUserLevel, 1)) {
                fixedPrice = item.getFirstFixedPrice();
            } else if (Objects.equals(sourceUserLevel, 2)) {
                fixedPrice = item.getSecondFixedPrice();
            } else {
                throw new IllegalArgumentException(StrUtil.format("User Level({}) Illegal", sourceUserLevel));
            }
            int brokeragePrice = calculatePrice(item.getBasePrice(), brokeragePercent, fixedPrice);
            if (brokeragePrice <= 0) {
                continue;
            }
            totalBrokerage += brokeragePrice;
            // Create record entity
            records.add(BrokerageRecordConvert.INSTANCE.convert(user, bizType, item.getBizId(),
                    brokerageFrozenDays, brokeragePrice, unfreezeTime, item.getTitle(),
                    item.getSourceUserId(), sourceUserLevel));
        }
        if (CollUtil.isEmpty(records)) {
            return;
        }
        // 1.3 Save commission records
        brokerageRecordMapper.insertBatch(records);

        // 2. Update user commission
        if (brokerageFrozenDays != null && brokerageFrozenDays > 0) { // Update user frozen commission
            brokerageUserService.updateUserFrozenPrice(user.getId(), totalBrokerage);
        } else { // Update user available commission
            brokerageUserService.updateUserPrice(user.getId(), totalBrokerage);
        }
    }

    @Override
    public int unfreezeRecord() {
        // 1. Query the commission records to be settled
        List<BrokerageRecordDO> records = brokerageRecordMapper.selectListByStatusAndUnfreezeTimeLt(
                BrokerageRecordStatusEnum.WAIT_SETTLEMENT.getStatus(), LocalDateTime.now());
        if (CollUtil.isEmpty(records)) {
            return 0;
        }

        // 2. Traversal execution
        int count = 0;
        for (BrokerageRecordDO record : records) {
            try {
                boolean success = getSelf().unfreezeRecord(record);
                if (success) {
                    count++;
                }
            } catch (Exception e) {
                log.error("[unfreezeRecord][record({}) Update to settled failed]", record.getId(), e);
            }
        }
        return count;
    }

    /**
     * Unfreeze a single commission record
     *
     * @param record Commission Record
     * @return Whether the thawing is successful
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean unfreezeRecord(BrokerageRecordDO record) {
        // Update record status
        BrokerageRecordDO updateObj = new BrokerageRecordDO()
                .setStatus(BrokerageRecordStatusEnum.SETTLEMENT.getStatus())
                .setUnfreezeTime(LocalDateTime.now());
        int updateRows = brokerageRecordMapper.updateByIdAndStatus(record.getId(), record.getStatus(), updateObj);
        if (updateRows == 0) {
            log.error("[unfreezeRecord][record({}) Update to settled failed]", record.getId());
            return false;
        }

        // Update user frozen commission
        brokerageUserService.updateFrozenPriceDecrAndPriceIncr(record.getUserId(), -record.getPrice());
        log.info("[unfreezeRecord][record({}) Updated to successful settlement]", record.getId());
        return true;
    }

    @Override
    public List<UserBrokerageSummaryRespBO> getUserBrokerageSummaryListByUserId(Collection<Long> userIds,
                                                                                Integer bizType, Integer status) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return brokerageRecordMapper.selectCountAndSumPriceByUserIdInAndBizTypeAndStatus(userIds, bizType, status);
    }

    @Override
    public Integer getSummaryPriceByUserId(Long userId, BrokerageRecordBizTypeEnum bizType, BrokerageRecordStatusEnum status,
                                           LocalDateTime beginTime, LocalDateTime endTime) {
        return brokerageRecordMapper.selectSummaryPriceByUserIdAndBizTypeAndCreateTimeBetween(userId,
                bizType.getType(), status.getStatus(), beginTime, endTime);
    }

    @Override
    public PageResult<AppBrokerageUserRankByPriceRespVO> getBrokerageUserChildSummaryPageByPrice(AppBrokerageUserRankPageReqVO pageReqVO) {
        IPage<AppBrokerageUserRankByPriceRespVO> pageResult = brokerageRecordMapper.selectSummaryPricePageGroupByUserId(
                MyBatisUtils.buildPage(pageReqVO),
                BrokerageRecordBizTypeEnum.ORDER.getType(), BrokerageRecordStatusEnum.SETTLEMENT.getStatus(),
                ArrayUtil.get(pageReqVO.getTimes(), 0), ArrayUtil.get(pageReqVO.getTimes(), 1));
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }

    @Override
    public Integer getUserRankByPrice(Long userId, LocalDateTime[] times) {
        // User's promotion amount
        Integer price = brokerageRecordMapper.selectSummaryPriceByUserIdAndBizTypeAndCreateTimeBetween(userId,
                BrokerageRecordBizTypeEnum.ORDER.getType(), BrokerageRecordStatusEnum.SETTLEMENT.getStatus(),
                ArrayUtil.get(times, 0), ArrayUtil.get(times, 1));
        // The number of people ahead of the user
        Integer greaterCount = brokerageRecordMapper.selectCountByPriceGt(price,
                BrokerageRecordBizTypeEnum.ORDER.getType(), BrokerageRecordStatusEnum.SETTLEMENT.getStatus(),
                ArrayUtil.get(times, 0), ArrayUtil.get(times, 1));
        // Get ranking
        return ObjUtil.defaultIfNull(greaterCount, 0) + 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBrokerage(Long userId, BrokerageRecordBizTypeEnum bizType, String bizId, Integer brokeragePrice, String title) {
        // 1. Check commission balance
        BrokerageUserDO user = brokerageUserService.getBrokerageUser(userId);
        int balance = Optional.of(user)
                .map(BrokerageUserDO::getBrokeragePrice).orElse(0);
        if (balance + brokeragePrice < 0) {
            throw exception(BROKERAGE_WITHDRAW_USER_BALANCE_NOT_ENOUGH, MoneyUtils.fenToYuanStr(balance));
        }

        // 2. Update commission balance
        boolean success = brokerageUserService.updateUserPrice(userId, brokeragePrice);
        if (!success) {
            // When failed，Throws an exception。Only appears when deducting commission，Insufficient balance
            throw exception(BROKERAGE_WITHDRAW_USER_BALANCE_NOT_ENOUGH, MoneyUtils.fenToYuanStr(balance));
        }

        // 3. Add new record
        BrokerageRecordDO record = BrokerageRecordConvert.INSTANCE.convert(user, bizType, bizId, 0, brokeragePrice,
                null, title, null, null);
        brokerageRecordMapper.insert(record);
    }

    @Override
    public AppBrokerageProductPriceRespVO calculateProductBrokeragePrice(Long userId, Long spuId) {
        // 1. Build a default return value
        AppBrokerageProductPriceRespVO respVO = new AppBrokerageProductPriceRespVO().setEnabled(false)
                .setBrokerageMinPrice(0).setBrokerageMaxPrice(0);

        // 2.1 Check whether the distribution function is enabled
        TradeConfigDO tradeConfig = tradeConfigService.getTradeConfig();
        if (tradeConfig == null || BooleanUtil.isFalse(tradeConfig.getBrokerageEnabled())) {
            return respVO;
        }
        // 2.2 Check whether the user is eligible for distribution
        respVO.setEnabled(brokerageUserService.getUserBrokerageEnabled(getLoginUserId()));
        if (BooleanUtil.isFalse(respVO.getEnabled())) {
            return respVO;
        }
        // 2.3 Check if the product exists
        ProductSpuRespDTO spu = productSpuApi.getSpu(spuId);
        if (spu == null) {
            return respVO;
        }

        // 3.1 Commission sharing mode for individual products
        Integer fixedMinPrice = 0;
        Integer fixedMaxPrice = 0;
        Integer spuMinPrice = 0;
        Integer spuMaxPrice = 0;
        List<ProductSkuRespDTO> skuList = productSkuApi.getSkuListBySpuId(ListUtil.of(spuId));
        if (BooleanUtil.isTrue(spu.getSubCommissionType())) {
            fixedMinPrice = getMinValue(skuList, ProductSkuRespDTO::getFirstBrokeragePrice);
            fixedMaxPrice = getMaxValue(skuList, ProductSkuRespDTO::getFirstBrokeragePrice);
            // 3.2 Global commission mode（Calculated based on the product price ratio）
        } else {
            spuMinPrice = getMinValue(skuList, ProductSkuRespDTO::getPrice);
            spuMaxPrice = getMaxValue(skuList, ProductSkuRespDTO::getPrice);
        }
        respVO.setBrokerageMinPrice(calculatePrice(spuMinPrice, tradeConfig.getBrokerageFirstPercent(), fixedMinPrice));
        respVO.setBrokerageMaxPrice(calculatePrice(spuMaxPrice, tradeConfig.getBrokerageFirstPercent(), fixedMaxPrice));
        return respVO;
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private BrokerageRecordServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
