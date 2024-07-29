package cn.econets.blossom.module.promotion.service.coupon;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.coupon.CouponPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon.AppCouponMatchReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponDO;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;

import java.util.*;

/**
 * Coupon Service Interface
 *
 */
public interface CouponService {

    /**
     * Check coupon，Include status、Limited period
     * <p>
     * 1. If the verification passes，Return coupon information
     * 2. If verification fails，Directly throw business exception
     *
     * @param id     Coupon number
     * @param userId User Number
     * @return Coupon Information
     */
    CouponDO validCoupon(Long id, Long userId);

    /**
     * Check coupon，Include status、Limited period
     *
     * @param coupon Coupon
     * @see #validCoupon(Long, Long) Logically the same，Just the input parameters are different
     */
    void validCoupon(CouponDO coupon);

    /**
     * Get coupons page
     *
     * @param pageReqVO Paged query
     * @return Coupon Paging
     */
    PageResult<CouponDO> getCouponPage(CouponPageReqVO pageReqVO);

    /**
     * Use coupon
     *
     * @param id      Coupon number
     * @param userId  User Number
     * @param orderId Order number
     */
    void useCoupon(Long id, Long userId, Long orderId);

    /**
     * Refund used coupons
     *
     * @param id Coupon Number
     */
    void returnUsedCoupon(Long id);

    /**
     * Recycle coupons
     *
     * @param id Coupon number
     */
    void deleteCoupon(Long id);

    /**
     * Get the user's coupon list
     *
     * @param userId User Number
     * @param status Coupon Status
     * @return Coupon List
     */
    List<CouponDO> getCouponList(Long userId, Integer status);

    /**
     * Get the number of unused coupons
     *
     * @param userId User Number
     * @return Number of unused coupons
     */
    Long getUnusedCouponCount(Long userId);

    /**
     * Get coupons
     *
     * @param templateId Coupon template number
     * @param userIds    User ID List
     * @param takeType   How to receive
     */
    void takeCoupon(Long templateId, Set<Long> userIds, CouponTakeTypeEnum takeType);

    /**
     * 【Administrator】Send coupons to users
     *
     * @param templateId Coupon template number
     * @param userIds    User ID list
     */
    default void takeCouponByAdmin(Long templateId, Set<Long> userIds) {
        takeCoupon(templateId, userIds, CouponTakeTypeEnum.ADMIN);
    }

    /**
     * 【Member】Get coupons
     *
     * @param templateId Coupon template number
     * @param userId     User Number
     */
    default void takeCouponByUser(Long templateId, Long userId) {
        takeCoupon(templateId, CollUtil.newHashSet(userId), CouponTakeTypeEnum.USER);
    }

    /**
     * 【System】Send new user coupons to users
     *
     * @param userId User Number
     */
    void takeCouponByRegister(Long userId);

    /**
     * Get the number of designated coupons received by members
     *
     * @param templateId Coupon template number
     * @param userId     User Number
     * @return Number of coupons received
     */
    default Integer getTakeCount(Long templateId, Long userId) {
        Map<Long, Integer> map = getTakeCountMapByTemplateIds(Collections.singleton(templateId), userId);
        return MapUtil.getInt(map, templateId, 0);
    }

    /**
     * Count the number of coupons received by members
     *
     * @param templateIds Coupon Template Number List
     * @param userId      User Number
     * @return Number of coupons received
     */
    Map<Long, Integer> getTakeCountMapByTemplateIds(Collection<Long> templateIds, Long userId);

    /**
     * Get the coupon list that matches the user
     *
     * @param userId     User Number
     * @param matchReqVO Matching parameters
     * @return Coupon List
     */
    List<CouponDO> getMatchCouponList(Long userId, AppCouponMatchReqVO matchReqVO);

    /**
     * Expired coupon
     *
     * @return Expired quantity
     */
    int expireCoupon();

    /**
     * Get whether the user can receive coupons
     *
     * @param userId    User Number
     * @param templates Coupon List
     * @return Can I claim it?
     */
    Map<Long, Boolean> getUserCanCanTakeMap(Long userId, List<CouponTemplateDO> templates);

    /**
     * Get coupons
     *
     * @param userId User Number
     * @param id     Number
     * @return Coupon
     */
    CouponDO getCoupon(Long userId, Long id);

}
