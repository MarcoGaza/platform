package cn.econets.blossom.module.trade.service.brokerage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.framework.mybatis.core.util.MyBatisUtils;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user.BrokerageUserPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserChildSummaryPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserChildSummaryRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankByUserCountRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankPageReqVO;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageUserConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import cn.econets.blossom.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.econets.blossom.module.trade.dal.mysql.brokerage.BrokerageUserMapper;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageBindModeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageEnabledConditionEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordStatusEnum;
import cn.econets.blossom.module.trade.service.config.TradeConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMapByFilter;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.*;

/**
 * Distribution User Service Implementation class
 *
 */
@Service
@Validated
public class BrokerageUserServiceImpl implements BrokerageUserService {

    @Resource
    private BrokerageUserMapper brokerageUserMapper;

    @Resource
    private TradeConfigService tradeConfigService;

    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public BrokerageUserDO getBrokerageUser(Long id) {
        return brokerageUserMapper.selectById(id);
    }

    @Override
    public List<BrokerageUserDO> getBrokerageUserList(Collection<Long> ids) {
        return brokerageUserMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BrokerageUserDO> getBrokerageUserPage(BrokerageUserPageReqVO pageReqVO) {
        List<Long> childIds = getChildUserIdsByLevel(pageReqVO.getBindUserId(), pageReqVO.getLevel());
        // Yes”Bind user ID“When querying conditions，No subordinate members found，Return empty directly
        if (pageReqVO.getBindUserId() != null && CollUtil.isEmpty(childIds)) {
            return PageResult.empty();
        }
        return brokerageUserMapper.selectPage(pageReqVO, childIds);
    }

    @Override
    public void updateBrokerageUserId(Long id, Long bindUserId) {
        // Verify existence
        BrokerageUserDO brokerageUser = validateBrokerageUserExists(id);
        // The binding relationship has not changed
        if (Objects.equals(brokerageUser.getBindUserId(), bindUserId)) {
            return;
        }

        // Situation 1：Clear promoters
        if (bindUserId == null) {
            // Clear promoters
            brokerageUserMapper.updateBindUserIdAndBindUserTimeToNull(id);
            return;
        }

        // Case 2：Modify promoter
        validateCanBindUser(brokerageUser, bindUserId);
        brokerageUserMapper.updateById(fillBindUserData(bindUserId, new BrokerageUserDO().setId(id)));
    }

    @Override
    public void updateBrokerageUserEnabled(Long id, Boolean enabled) {
        // Verify existence
        validateBrokerageUserExists(id);
        if (BooleanUtil.isTrue(enabled)) {
            // Open promotion qualification
            brokerageUserMapper.updateById(new BrokerageUserDO().setId(id)
                    .setBrokerageEnabled(true).setBrokerageTime(LocalDateTime.now()));
        } else {
            // Cancel promotion qualification
            brokerageUserMapper.updateEnabledFalseAndBrokerageTimeToNull(id);
        }
    }

    private BrokerageUserDO validateBrokerageUserExists(Long id) {
        BrokerageUserDO brokerageUserDO = brokerageUserMapper.selectById(id);
        if (brokerageUserDO == null) {
            throw exception(BROKERAGE_USER_NOT_EXISTS);
        }

        return brokerageUserDO;
    }

    @Override
    public BrokerageUserDO getBindBrokerageUser(Long id) {
        return Optional.ofNullable(id)
                .map(this::getBrokerageUser)
                .map(BrokerageUserDO::getBindUserId)
                .map(this::getBrokerageUser)
                .orElse(null);
    }

    @Override
    public boolean updateUserPrice(Long id, Integer price) {
        if (price > 0) {
            brokerageUserMapper.updatePriceIncr(id, price);
        } else if (price < 0) {
            return brokerageUserMapper.updatePriceDecr(id, price) > 0;
        }
        return true;
    }

    @Override
    public void updateUserFrozenPrice(Long id, Integer frozenPrice) {
        if (frozenPrice > 0) {
            brokerageUserMapper.updateFrozenPriceIncr(id, frozenPrice);
        } else if (frozenPrice < 0) {
            brokerageUserMapper.updateFrozenPriceDecr(id, frozenPrice);
        }
    }

    @Override
    public void updateFrozenPriceDecrAndPriceIncr(Long id, Integer frozenPrice) {
        Assert.isTrue(frozenPrice < 0);
        int updateRows = brokerageUserMapper.updateFrozenPriceDecrAndPriceIncr(id, frozenPrice);
        if (updateRows == 0) {
            throw exception(BROKERAGE_USER_FROZEN_PRICE_NOT_ENOUGH);
        }
    }

    @Override
    public Long getBrokerageUserCountByBindUserId(Long bindUserId, Integer level) {
        List<Long> childIds = getChildUserIdsByLevel(bindUserId, level);
        return (long) CollUtil.size(childIds);
    }

    @Override
    public boolean bindBrokerageUser(Long userId, Long bindUserId) {
        // 1. Get distribution users
        boolean isNewBrokerageUser = false;
        BrokerageUserDO brokerageUser = brokerageUserMapper.selectById(userId);
        if (brokerageUser == null) { // Distribution user does not exist：1. New registration；2. Old data；3. The distribution function was turned off and then turned on again
            isNewBrokerageUser = true;
            brokerageUser = new BrokerageUserDO().setId(userId).setBrokerageEnabled(false).setBrokeragePrice(0).setFrozenPrice(0);
        }

        // 2.1 Check whether the user can be bound
        boolean validated = isUserCanBind(brokerageUser);
        if (!validated) {
            return false;
        }
        // 2.3 Check whether binding is possible
        validateCanBindUser(brokerageUser, bindUserId);
        // 2.3 Bind user
        if (isNewBrokerageUser) {
            Integer enabledCondition = tradeConfigService.getTradeConfig().getBrokerageEnabledCondition();
            if (BrokerageEnabledConditionEnum.ALL.getCondition().equals(enabledCondition)) { // Everyone distributes：Users are eligible for distribution by default
                brokerageUser.setBrokerageEnabled(true).setBrokerageTime(LocalDateTime.now());
            }
            brokerageUser.setBindUserId(bindUserId).setBindUserTime(LocalDateTime.now());
            brokerageUserMapper.insert(fillBindUserData(bindUserId, brokerageUser));
        } else {
            brokerageUserMapper.updateById(fillBindUserData(bindUserId, new BrokerageUserDO().setId(userId)));
        }
        return true;
    }

    /**
     * Complete the fields for binding users
     *
     * @param bindUserId    Bound user number
     * @param brokerageUser update Object
     * @return Completed update Object
     */
    private BrokerageUserDO fillBindUserData(Long bindUserId, BrokerageUserDO brokerageUser) {
        return brokerageUser.setBindUserId(bindUserId).setBindUserTime(LocalDateTime.now());
    }

    @Override
    public Boolean getUserBrokerageEnabled(Long userId) {
        // Is the global distribution function enabled?
        TradeConfigDO tradeConfig = tradeConfigService.getTradeConfig();
        if (tradeConfig == null || BooleanUtil.isFalse(tradeConfig.getBrokerageEnabled())) {
            return false;
        }

        // Is the user eligible for distribution?
        return Optional.ofNullable(getBrokerageUser(userId))
                .map(BrokerageUserDO::getBrokerageEnabled)
                .orElse(false);
    }

    @Override
    public PageResult<AppBrokerageUserRankByUserCountRespVO> getBrokerageUserRankPageByUserCount(AppBrokerageUserRankPageReqVO pageReqVO) {
        IPage<AppBrokerageUserRankByUserCountRespVO> pageResult = brokerageUserMapper.selectCountPageGroupByBindUserId(MyBatisUtils.buildPage(pageReqVO),
                ArrayUtil.get(pageReqVO.getTimes(), 0), ArrayUtil.get(pageReqVO.getTimes(), 1));
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }

    @Override
    public PageResult<AppBrokerageUserChildSummaryRespVO> getBrokerageUserChildSummaryPage(AppBrokerageUserChildSummaryPageReqVO pageReqVO, Long userId) {
        // 1.1 Query the list of subordinate user numbers
        List<Long> childIds = getChildUserIdsByLevel(userId, pageReqVO.getLevel());
        if (CollUtil.isEmpty(childIds)) {
            return PageResult.empty();
        }
        // 1.2 Filter subordinate users by nickname
        List<MemberUserRespDTO> users = memberUserApi.getUserList(childIds);
        Map<Long, MemberUserRespDTO> userMap = convertMapByFilter(users,
                user -> StrUtil.contains(user.getNickname(), pageReqVO.getNickname()),
                MemberUserRespDTO::getId);
        if (CollUtil.isEmpty(userMap)) {
            return PageResult.empty();
        }

        // 2. Paged query
        IPage<AppBrokerageUserChildSummaryRespVO> pageResult = brokerageUserMapper.selectSummaryPageByUserId(
                MyBatisUtils.buildPage(pageReqVO), BrokerageRecordBizTypeEnum.ORDER.getType(),
                BrokerageRecordStatusEnum.SETTLEMENT.getStatus(), userMap.keySet(), pageReqVO.getSortingField()
        );

        // 3. Join data and return
        BrokerageUserConvert.INSTANCE.copyTo(pageResult.getRecords(), userMap);
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal());
    }

    private boolean isUserCanBind(BrokerageUserDO user) {
        // Check whether the distribution function is enabled
        TradeConfigDO tradeConfig = tradeConfigService.getTradeConfig();
        if (tradeConfig == null || !BooleanUtil.isTrue(tradeConfig.getBrokerageEnabled())) {
            return false;
        }

        // Verify commission mode：Promoters can only be set manually in the background
        if (BrokerageEnabledConditionEnum.ADMIN.getCondition().equals(tradeConfig.getBrokerageEnabledCondition())) {
            throw exception(BROKERAGE_BIND_CONDITION_ADMIN);
        }

        // Verify the distribution relationship binding mode
        if (BrokerageBindModeEnum.REGISTER.getMode().equals(tradeConfig.getBrokerageBindMode())) {
            // Judge whether it is a new user：Registration time is 30 Within seconds，All are considered new users
            if (!isNewRegisterUser(user.getId())) {
                throw exception(BROKERAGE_BIND_MODE_REGISTER); // Can only be bound when registering
            }
        } else if (BrokerageBindModeEnum.ANYTIME.getMode().equals(tradeConfig.getBrokerageBindMode())) {
            if (user.getBindUserId() != null) {
                throw exception(BROKERAGE_BIND_OVERRIDE); // Already bound to promoter
            }
        }
        return true;
    }

    /**
     * Judge whether it is a new user
     * <p>
     * Standard：Registration time is 30 Within seconds，All are considered new users
     * <p>
     * Question：Why is it achieved in this way?？
     * Answer：Because registered in member Module，Hope it and trade Module decoupling，So we can only use this agreed logic。
     *
     * @param userId User Number
     * @return Is it a new user?
     */
    private boolean isNewRegisterUser(Long userId) {
        MemberUserRespDTO user = memberUserApi.getUser(userId);
        return user != null && LocalDateTimeUtils.beforeNow(user.getCreateTime().plusSeconds(30));
    }

    private void validateCanBindUser(BrokerageUserDO user, Long bindUserId) {
        // Check whether the user to be bound is eligible for promotion
        BrokerageUserDO bindUser = brokerageUserMapper.selectById(bindUserId);
        if (bindUser == null || BooleanUtil.isFalse(bindUser.getBrokerageEnabled())) {
            throw exception(BROKERAGE_BIND_USER_NOT_ENABLED);
        }

        // Verify binding yourself
        if (Objects.equals(user.getId(), bindUserId)) {
            throw exception(BROKERAGE_BIND_SELF);
        }

        // Subordinates cannot bind to their superiors
        for (int i = 0; i <= Short.MAX_VALUE; i++) {
            if (Objects.equals(bindUser.getBindUserId(), user.getId())) {
                throw exception(BROKERAGE_BIND_LOOP);
            }
            bindUser = getBrokerageUser(bindUser.getBindUserId());
            // Found the root node，End loop
            if (bindUser == null || bindUser.getBindUserId() == null) {
                break;
            }
        }
    }

    /**
     * According to the bound user ID，Get the list of subordinate user numbers
     *
     * @param bindUserId Bind user ID
     * @param level      Level of subordinate users。
     *                   If level Empty，Then query 1+2 Two levels
     * @return Subordinate user number list
     */
    private List<Long> getChildUserIdsByLevel(Long bindUserId, Integer level) {
        if (bindUserId == null) {
            return Collections.emptyList();
        }
        // Check the first 1 Level
        List<Long> bindUserIds = brokerageUserMapper.selectIdListByBindUserIdIn(Collections.singleton(bindUserId));
        if (CollUtil.isEmpty(bindUserIds)) {
            return Collections.emptyList();
        }

        // Situation 1：level Empty，Query all levels
        if (level == null) {
            // Check again 2 Level，And merge the results
            bindUserIds.addAll(brokerageUserMapper.selectIdListByBindUserIdIn(bindUserIds));
            return bindUserIds;
        }
        // Case 2：level for 1，Only query the first 1 Level
        if (level == 1) {
            return bindUserIds;
        }
        // Case 3：level for 1，Only query the first 2 Level
        if (level == 2) {
            return brokerageUserMapper.selectIdListByBindUserIdIn(bindUserIds);
        }
        throw exception(BROKERAGE_USER_LEVEL_NOT_SUPPORT);
    }

}
