package cn.econets.blossom.module.promotion.service.coupon;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template.CouponTemplateCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template.CouponTemplatePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template.CouponTemplateUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Coupon Template Service Interface
 *
 */
public interface CouponTemplateService {

    /**
     * Create coupon template
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createCouponTemplate(@Valid CouponTemplateCreateReqVO createReqVO);

    /**
     * Update coupon template
     *
     * @param updateReqVO Update information
     */
    void updateCouponTemplate(@Valid CouponTemplateUpdateReqVO updateReqVO);

    /**
     * Update the status of the coupon template
     *
     * @param id     Number
     * @param status Status
     */
    void updateCouponTemplateStatus(Long id, Integer status);

    /**
     * Delete coupon template
     *
     * @param id Number
     */
    void deleteCouponTemplate(Long id);

    /**
     * Get coupon template
     *
     * @param id Number
     * @return Coupon Template
     */
    CouponTemplateDO getCouponTemplate(Long id);

    /**
     * Get coupon template page
     *
     * @param pageReqVO Paged query
     * @return Coupon template pagination
     */
    PageResult<CouponTemplateDO> getCouponTemplatePage(CouponTemplatePageReqVO pageReqVO);

    /**
     * Update the number of coupon templates to receive
     *
     * @param id        Coupon template number
     * @param incrCount Increase quantity
     */
    void updateCouponTemplateTakeCount(Long id, int incrCount);

    /**
     * Get the coupon template for the specified collection method
     *
     * @param takeType How to receive
     * @return Coupon Template List
     */
    List<CouponTemplateDO> getCouponTemplateListByTakeType(CouponTakeTypeEnum takeType);

    /**
     * Get the coupon template list
     *
     * @param canTakeTypes      List of available types
     * @param productScope      Product usage scope type
     * @param productScopeValue Product usage range number
     * @param count             Query quantity
     * @return Coupon Template List
     */
    List<CouponTemplateDO> getCouponTemplateList(List<Integer> canTakeTypes, Integer productScope,
                                                 Long productScopeValue, Integer count);

    /**
     * Get coupon template list
     *
     * @param ids Coupon template number
     * @return Coupon Template List
     */
    List<CouponTemplateDO> getCouponTemplateList(Collection<Long> ids);

}
