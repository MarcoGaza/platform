package cn.econets.blossom.module.trade.service.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderPageReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderSummaryRespVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderPageReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.dal.mysql.order.TradeOrderItemMapper;
import cn.econets.blossom.module.trade.dal.mysql.order.TradeOrderMapper;
import cn.econets.blossom.module.trade.dal.redis.RedisKeyConstants;
import cn.econets.blossom.module.trade.enums.order.TradeOrderRefundStatusEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClientFactory;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackQueryReqDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;
import cn.econets.blossom.module.trade.service.delivery.DeliveryExpressService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_NOT_EXISTS;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.ORDER_NOT_FOUND;

/**
 * Transaction Order【Read】 Service Implementation class
 *
 */
@Service
public class TradeOrderQueryServiceImpl implements TradeOrderQueryService {

    @Resource
    private ExpressClientFactory expressClientFactory;

    @Resource
    private TradeOrderMapper tradeOrderMapper;
    @Resource
    private TradeOrderItemMapper tradeOrderItemMapper;

    @Resource
    private DeliveryExpressService deliveryExpressService;

    @Resource
    private MemberUserApi memberUserApi;

    // =================== Order ===================

    @Override
    public TradeOrderDO getOrder(Long id) {
        return tradeOrderMapper.selectById(id);
    }

    @Override
    public TradeOrderDO getOrder(Long userId, Long id) {
        TradeOrderDO order = tradeOrderMapper.selectById(id);
        if (order != null
                && ObjectUtil.notEqual(order.getUserId(), userId)) {
            return null;
        }
        return order;
    }

    @Override
    public TradeOrderDO getOrderByUserIdAndStatusAndCombination(Long userId, Long combinationActivityId, Integer status) {
        return tradeOrderMapper.selectByUserIdAndCombinationActivityIdAndStatus(userId, combinationActivityId, status);
    }

    @Override
    public List<TradeOrderDO> getOrderList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return tradeOrderMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TradeOrderDO> getOrderPage(TradeOrderPageReqVO reqVO) {
        // Build a user ID list based on user query conditions
        Set<Long> userIds = buildQueryConditionUserIds(reqVO);
        if (userIds == null) { // No user found，This means there is definitely no order from him
            return PageResult.empty();
        }
        // Paged query
        return tradeOrderMapper.selectPage(reqVO, userIds);
    }

    private Set<Long> buildQueryConditionUserIds(TradeOrderPageReqVO reqVO) {
        // Get userId Related queries
        Set<Long> userIds = new HashSet<>();
        if (StrUtil.isNotEmpty(reqVO.getUserMobile())) {
            MemberUserRespDTO user = memberUserApi.getUserByMobile(reqVO.getUserMobile());
            if (user == null) { // No user found，This means there is definitely no order from him
                return null;
            }
            userIds.add(user.getId());
        }
        if (StrUtil.isNotEmpty(reqVO.getUserNickname())) {
            List<MemberUserRespDTO> users = memberUserApi.getUserListByNickname(reqVO.getUserNickname());
            if (CollUtil.isEmpty(users)) { // No user found，This means there is definitely no order from him
                return null;
            }
            userIds.addAll(convertSet(users, MemberUserRespDTO::getId));
        }
        return userIds;
    }

    @Override
    public TradeOrderSummaryRespVO getOrderSummary(TradeOrderPageReqVO reqVO) {
        // Build a user ID list based on user query conditions
        Set<Long> userIds = buildQueryConditionUserIds(reqVO);
        if (userIds == null) { // No user found，This means there is definitely no order from him
            return new TradeOrderSummaryRespVO();
        }
        // Query the quantity corresponding to each after-sales status、Amount
        List<Map<String, Object>> list = tradeOrderMapper.selectOrderSummaryGroupByRefundStatus(reqVO, null);

        TradeOrderSummaryRespVO vo = new TradeOrderSummaryRespVO().setAfterSaleCount(0L).setAfterSalePrice(0L);
        for (Map<String, Object> map : list) {
            Long count = MapUtil.getLong(map, "count", 0L);
            Long price = MapUtil.getLong(map, "price", 0L);
            // Unrefundable orders，Partial Refund、All refunds will be included in after-sales service
            if (TradeOrderRefundStatusEnum.NONE.getStatus().equals(MapUtil.getInt(map, "refundStatus"))) {
                vo.setOrderCount(count).setOrderPayPrice(price);
            } else {
                vo.setAfterSaleCount(vo.getAfterSaleCount() + count).setAfterSalePrice(vo.getAfterSalePrice() + price);
            }
        }
        return vo;
    }

    @Override
    public PageResult<TradeOrderDO> getOrderPage(Long userId, AppTradeOrderPageReqVO reqVO) {
        return tradeOrderMapper.selectPage(reqVO, userId);
    }

    @Override
    public Long getOrderCount(Long userId, Integer status, Boolean commentStatus) {
        return tradeOrderMapper.selectCountByUserIdAndStatus(userId, status, commentStatus);
    }

    @Override
    public List<ExpressTrackRespDTO> getExpressTrackList(Long id, Long userId) {
        // Query order
        TradeOrderDO order = tradeOrderMapper.selectByIdAndUserId(id, userId);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // Query logistics
        return getExpressTrackList(order);
    }

    @Override
    public List<ExpressTrackRespDTO> getExpressTrackList(Long id) {
        // Query order
        TradeOrderDO order = tradeOrderMapper.selectById(id);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // Query logistics
        return getExpressTrackList(order);
    }

    @Override
    public int getSeckillProductCount(Long userId, Long activityId) {
        // Get order list
        List<TradeOrderDO> orders = tradeOrderMapper.selectListByUserIdAndSeckillActivityId(userId, activityId);
        orders.removeIf(order -> TradeOrderStatusEnum.isCanceled(order.getStatus())); // Filter out【Cancelled】Orders
        if (CollUtil.isEmpty(orders)) {
            return 0;
        }
        // Get the list of order items
        return tradeOrderItemMapper.selectProductSumByOrderId(convertSet(orders, TradeOrderDO::getId));
    }

    /**
     * Get the logistics track of the order
     *
     * @param order Order
     * @return Logistics track
     */
    private List<ExpressTrackRespDTO> getExpressTrackList(TradeOrderDO order) {
        if (order.getLogisticsId() == null) {
            return Collections.emptyList();
        }
        // Query logistics company
        DeliveryExpressDO express = deliveryExpressService.getDeliveryExpress(order.getLogisticsId());
        if (express == null) {
            throw exception(EXPRESS_NOT_EXISTS);
        }
        // Query logistics track
        return getSelf().getExpressTrackList(express.getCode(), order.getLogisticsNo(), order.getReceiverMobile());
    }

    /**
     * Query logistics track
     *
     * Purpose of caching：Considering that timeliness is not a high requirement，But each call requires money
     *
     * @param code           Express delivery company code
     * @param logisticsNo    Shipping express number
     * @param receiverMobile Receive、Sender's phone number
     * @return Logistics track
     */
    @Cacheable(cacheNames = RedisKeyConstants.EXPRESS_TRACK, key = "#code + '-' + #logisticsNo + '-' + #receiverMobile",
            condition = "#result != null")
    public List<ExpressTrackRespDTO> getExpressTrackList(String code, String logisticsNo, String receiverMobile) {
        return expressClientFactory.getDefaultExpressClient().getExpressTrackList(
                new ExpressTrackQueryReqDTO().setExpressCode(code).setLogisticsNo(logisticsNo)
                        .setPhone(receiverMobile));
    }


    // =================== Order Item ===================

    @Override
    public TradeOrderItemDO getOrderItem(Long userId, Long itemId) {
        TradeOrderItemDO orderItem = tradeOrderItemMapper.selectById(itemId);
        if (orderItem != null
                && ObjectUtil.notEqual(orderItem.getUserId(), userId)) {
            return null;
        }
        return orderItem;
    }

    @Override
    public TradeOrderItemDO getOrderItem(Long id) {
        return tradeOrderItemMapper.selectById(id);
    }

    @Override
    public List<TradeOrderItemDO> getOrderItemListByOrderId(Collection<Long> orderIds) {
        if (CollUtil.isEmpty(orderIds)) {
            return Collections.emptyList();
        }
        return tradeOrderItemMapper.selectListByOrderId(orderIds);
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private TradeOrderQueryServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
