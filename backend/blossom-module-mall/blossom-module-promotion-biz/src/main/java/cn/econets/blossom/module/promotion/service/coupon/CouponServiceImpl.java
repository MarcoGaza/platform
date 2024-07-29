package cn.econets.blossom.module.promotion.service.coupon;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.coupon.CouponPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon.AppCouponMatchReqVO;
import cn.econets.blossom.module.promotion.convert.coupon.CouponConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponDO;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import cn.econets.blossom.module.promotion.dal.mysql.coupon.CouponMapper;
import cn.econets.blossom.module.promotion.enums.coupon.CouponStatusEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTemplateValidityTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;
import static java.util.Arrays.asList;

/**
 * Coupon Service Implementation class
 *
 */
@Slf4j
@Service
@Validated
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponTemplateService couponTemplateService;

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public CouponDO validCoupon(Long id, Long userId) {
        CouponDO coupon = couponMapper.selectByIdAndUserId(id, userId);
        if (coupon == null) {
            throw exception(COUPON_NOT_EXISTS);
        }
        validCoupon(coupon);
        return coupon;
    }

    @Override
    public void validCoupon(CouponDO coupon) {
        // Verification Status
        if (ObjectUtil.notEqual(coupon.getStatus(), CouponStatusEnum.UNUSED.getStatus())) {
            throw exception(COUPON_STATUS_NOT_UNUSED);
        }
        // Verify validity period；To prevent the timer from running out，The actual coupon has expired
        if (!LocalDateTimeUtils.isBetween(coupon.getValidStartTime(), coupon.getValidEndTime())) {
            throw exception(COUPON_VALID_TIME_NOT_NOW);
        }
    }

    @Override
    public PageResult<CouponDO> getCouponPage(CouponPageReqVO pageReqVO) {
        // Get user ID
        if (StrUtil.isNotEmpty(pageReqVO.getNickname())) {
            List<MemberUserRespDTO> users = memberUserApi.getUserListByNickname(pageReqVO.getNickname());
            if (CollUtil.isEmpty(users)) {
                return PageResult.empty();
            }
            pageReqVO.setUserIds(convertSet(users, MemberUserRespDTO::getId));
        }
        // Paged query
        return couponMapper.selectPage(pageReqVO);
    }

    @Override
    public void useCoupon(Long id, Long userId, Long orderId) {
        // Check coupon
        validCoupon(id, userId);

        // Update status
        int updateCount = couponMapper.updateByIdAndStatus(id, CouponStatusEnum.UNUSED.getStatus(),
                new CouponDO().setStatus(CouponStatusEnum.USED.getStatus())
                        .setUseOrderId(orderId).setUseTime(LocalDateTime.now()));
        if (updateCount == 0) {
            throw exception(COUPON_STATUS_NOT_UNUSED);
        }
    }

    @Override
    public void returnUsedCoupon(Long id) {
        // Check existence
        CouponDO coupon = couponMapper.selectById(id);
        if (coupon == null) {
            throw exception(COUPON_NOT_EXISTS);
        }
        // Verification status
        if (ObjectUtil.notEqual(coupon.getStatus(), CouponStatusEnum.USED.getStatus())) {
            throw exception(COUPON_STATUS_NOT_USED);
        }

        // Refund
        Integer status = LocalDateTimeUtils.beforeNow(coupon.getValidEndTime())
                ? CouponStatusEnum.EXPIRE.getStatus() // It may have expired when returned
                : CouponStatusEnum.UNUSED.getStatus();
        int updateCount = couponMapper.updateByIdAndStatus(id, CouponStatusEnum.UNUSED.getStatus(),
                new CouponDO().setStatus(status));
        if (updateCount == 0) {
            throw exception(COUPON_STATUS_NOT_USED);
        }

        // TODO Add coupon change record？
    }

    @Override
    @Transactional
    public void deleteCoupon(Long id) {
        // Check existence
        validateCouponExists(id);

        // Update coupons
        int deleteCount = couponMapper.delete(id,
                asList(CouponStatusEnum.UNUSED.getStatus(), CouponStatusEnum.EXPIRE.getStatus()));
        if (deleteCount == 0) {
            throw exception(COUPON_DELETE_FAIL_USED);
        }
        // Reduce the number of coupon templates that can be redeemed -1
        couponTemplateService.updateCouponTemplateTakeCount(id, -1);
    }

    @Override
    public List<CouponDO> getCouponList(Long userId, Integer status) {
        return couponMapper.selectListByUserIdAndStatus(userId, status);
    }

    private void validateCouponExists(Long id) {
        if (couponMapper.selectById(id) == null) {
            throw exception(COUPON_NOT_EXISTS);
        }
    }

    @Override
    public Long getUnusedCouponCount(Long userId) {
        return couponMapper.selectCountByUserIdAndStatus(userId, CouponStatusEnum.UNUSED.getStatus());
    }

    @Override
    public void takeCoupon(Long templateId, Set<Long> userIds, CouponTakeTypeEnum takeType) {
        CouponTemplateDO template = couponTemplateService.getCouponTemplate(templateId);
        // 1. Filter out users who have reached the claim limit
        removeTakeLimitUser(userIds, template);
        // 2. Check whether the coupon can be received
        validateCouponTemplateCanTake(template, userIds, takeType);

        // 3. Batch save coupons
        couponMapper.insertBatch(convertList(userIds, userId -> CouponConvert.INSTANCE.convert(template, userId)));

        // 3. Increase the number of coupon templates that can be redeemed
        couponTemplateService.updateCouponTemplateTakeCount(templateId, userIds.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void takeCouponByRegister(Long userId) {
        List<CouponTemplateDO> templates = couponTemplateService.getCouponTemplateListByTakeType(CouponTakeTypeEnum.REGISTER);
        for (CouponTemplateDO template : templates) {
            takeCoupon(template.getId(), CollUtil.newHashSet(userId), CouponTakeTypeEnum.REGISTER);
        }
    }

    @Override
    public Map<Long, Integer> getTakeCountMapByTemplateIds(Collection<Long> templateIds, Long userId) {
        if (CollUtil.isEmpty(templateIds)) {
            return Collections.emptyMap();
        }
        return couponMapper.selectCountByUserIdAndTemplateIdIn(userId, templateIds);
    }

    @Override
    public List<CouponDO> getMatchCouponList(Long userId, AppCouponMatchReqVO matchReqVO) {
        return couponMapper.selectListByUserIdAndStatusAndUsePriceLeAndProductScope(userId,
                CouponStatusEnum.UNUSED.getStatus(),
                matchReqVO.getPrice(), matchReqVO.getSpuIds(), matchReqVO.getCategoryIds());
    }

    @Override
    public int expireCoupon() {
        // 1. Query the coupons to be expired
        List<CouponDO> list = couponMapper.selectListByStatusAndValidEndTimeLe(
                CouponStatusEnum.UNUSED.getStatus(), LocalDateTime.now());
        if (CollUtil.isEmpty(list)) {
            return 0;
        }

        // 2. Traversal execution
        int count = 0;
        for (CouponDO coupon : list) {
            try {
                boolean success = getSelf().expireCoupon(coupon);
                if (success) {
                    count++;
                }
            } catch (Exception e) {
                log.error("[expireCoupon][coupon({}) Update to expired failed]", coupon.getId(), e);
            }
        }
        return count;
    }

    @Override
    public Map<Long, Boolean> getUserCanCanTakeMap(Long userId, List<CouponTemplateDO> templates) {
        // 1. Not logged in，All are displayed as available for collection
        Map<Long, Boolean> userCanTakeMap = convertMap(templates, CouponTemplateDO::getId, templateId -> true);
        if (userId == null) {
            return userCanTakeMap;
        }

        // 2.1 Filter the number of items that can be collected without any limit
        Set<Long> templateIds = convertSet(templates, CouponTemplateDO::getId, template -> template.getTakeLimitCount() != -1);
        // 2.2 Check whether the amount received by the user exceeds the limit
        if (CollUtil.isNotEmpty(templateIds)) {
            Map<Long, Integer> couponTakeCountMap = this.getTakeCountMapByTemplateIds(templateIds, userId);
            for (CouponTemplateDO template : templates) {
                Integer takeCount = couponTakeCountMap.get(template.getId());
                userCanTakeMap.put(template.getId(), takeCount == null || takeCount < template.getTakeLimitCount());
            }
        }
        return userCanTakeMap;
    }

    /**
     * Expired single coupon
     *
     * @param coupon Coupon
     * @return Whether the expiration is successful
     */
    private boolean expireCoupon(CouponDO coupon) {
        // Update record status
        int updateRows = couponMapper.updateByIdAndStatus(coupon.getId(), CouponStatusEnum.UNUSED.getStatus(),
                new CouponDO().setStatus(CouponStatusEnum.EXPIRE.getStatus()));
        if (updateRows == 0) {
            log.error("[expireCoupon][coupon({}) Update to expired failed]", coupon.getId());
            return false;
        }
        log.info("[expireCoupon][coupon({}) Updated to expired successfully]", coupon.getId());
        return true;
    }

    /**
     * Check whether the coupon can be collected
     *
     * @param couponTemplate Coupon Template
     * @param userIds        Recipient List
     * @param takeType       How to receive
     */
    private void validateCouponTemplateCanTake(CouponTemplateDO couponTemplate, Set<Long> userIds, CouponTakeTypeEnum takeType) {
        // If all users have received it，Throws an exception
        if (CollUtil.isEmpty(userIds)) {
            throw exception(COUPON_TEMPLATE_USER_ALREADY_TAKE);
        }

        // Verification template
        if (couponTemplate == null) {
            throw exception(COUPON_TEMPLATE_NOT_EXISTS);
        }
        // Check remaining quantity
        if (couponTemplate.getTakeCount() + userIds.size() > couponTemplate.getTotalCount()) {
            throw exception(COUPON_TEMPLATE_NOT_ENOUGH);
        }
        // Verification"Fixed date"Is the validity period type expired?
        if (CouponTemplateValidityTypeEnum.DATE.getType().equals(couponTemplate.getValidityType())) {
            if (LocalDateTimeUtils.beforeNow(couponTemplate.getValidEndTime())) {
                throw exception(COUPON_TEMPLATE_EXPIRED);
            }
        }
        // Verify the method of collection
        if (ObjectUtil.notEqual(couponTemplate.getTakeType(), takeType.getValue())) {
            throw exception(COUPON_TEMPLATE_CANNOT_TAKE);
        }
    }

    /**
     * Filter out users who have reached the upper limit of claiming
     *
     * @param userIds        User ID array
     * @param couponTemplate Coupon Template
     */
    private void removeTakeLimitUser(Set<Long> userIds, CouponTemplateDO couponTemplate) {
        if (couponTemplate.getTakeLimitCount() <= 0) {
            return;
        }
        // Query users who have received coupons
        List<CouponDO> alreadyTakeCoupons = couponMapper.selectListByTemplateIdAndUserId(couponTemplate.getId(), userIds);
        if (CollUtil.isEmpty(alreadyTakeCoupons)) {
            return;
        }
        // Remove users who have reached the claim limit
        Map<Long, Integer> userTakeCountMap = CollStreamUtil.groupBy(alreadyTakeCoupons, CouponDO::getUserId, Collectors.summingInt(c -> 1));
        userIds.removeIf(userId -> MapUtil.getInt(userTakeCountMap, userId, 0) >= couponTemplate.getTakeLimitCount());
    }

    @Override
    public CouponDO getCoupon(Long userId, Long id) {
        return couponMapper.selectByIdAndUserId(id, userId);
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private CouponServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }
}
