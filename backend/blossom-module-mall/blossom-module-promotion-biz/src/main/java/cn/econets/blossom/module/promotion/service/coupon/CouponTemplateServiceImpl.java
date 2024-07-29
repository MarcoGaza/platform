package cn.econets.blossom.module.promotion.service.coupon;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.api.category.ProductCategoryApi;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template.CouponTemplateCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template.CouponTemplatePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template.CouponTemplateUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.coupon.CouponTemplateConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import cn.econets.blossom.module.promotion.dal.mysql.coupon.CouponTemplateMapper;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.COUPON_TEMPLATE_NOT_EXISTS;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.COUPON_TEMPLATE_TOTAL_COUNT_TOO_SMALL;

/**
 * Coupon Template Service Implementation class
 *
 */
@Service
@Validated
public class CouponTemplateServiceImpl implements CouponTemplateService {

    @Resource
    private CouponTemplateMapper couponTemplateMapper;

    @Resource
    private ProductCategoryApi productCategoryApi;
    @Resource
    private ProductSpuApi productSpuApi;

    @Override
    public Long createCouponTemplate(CouponTemplateCreateReqVO createReqVO) {
        // Check product range
        validateProductScope(createReqVO.getProductScope(), createReqVO.getProductScopeValues());
        // Insert
        CouponTemplateDO couponTemplate = CouponTemplateConvert.INSTANCE.convert(createReqVO)
                .setStatus(CommonStatusEnum.ENABLE.getStatus());
        couponTemplateMapper.insert(couponTemplate);
        // Return
        return couponTemplate.getId();
    }

    @Override
    public void updateCouponTemplate(CouponTemplateUpdateReqVO updateReqVO) {
        // Check existence
        CouponTemplateDO couponTemplate = validateCouponTemplateExists(updateReqVO.getId());
        // The verification and issuance quantity cannot be too small
        if (updateReqVO.getTotalCount() < couponTemplate.getTakeCount()) {
            throw exception(COUPON_TEMPLATE_TOTAL_COUNT_TOO_SMALL, couponTemplate.getTakeCount());
        }
        // Check product range
        validateProductScope(updateReqVO.getProductScope(), updateReqVO.getProductScopeValues());

        // Update
        CouponTemplateDO updateObj = CouponTemplateConvert.INSTANCE.convert(updateReqVO);
        couponTemplateMapper.updateById(updateObj);
    }

    @Override
    public void updateCouponTemplateStatus(Long id, Integer status) {
        // Check existence
        validateCouponTemplateExists(id);
        // Update
        couponTemplateMapper.updateById(new CouponTemplateDO().setId(id).setStatus(status));
    }

    @Override
    public void deleteCouponTemplate(Long id) {
        // Check existence
        validateCouponTemplateExists(id);
        // Delete
        couponTemplateMapper.deleteById(id);
    }

    private CouponTemplateDO validateCouponTemplateExists(Long id) {
        CouponTemplateDO couponTemplate = couponTemplateMapper.selectById(id);
        if (couponTemplate == null) {
            throw exception(COUPON_TEMPLATE_NOT_EXISTS);
        }
        return couponTemplate;
    }

    private void validateProductScope(Integer productScope, List<Long> productScopeValues) {
        if (Objects.equals(PromotionProductScopeEnum.SPU.getScope(), productScope)) {
            productSpuApi.validateSpuList(productScopeValues);
        } else if (Objects.equals(PromotionProductScopeEnum.CATEGORY.getScope(), productScope)) {
            productCategoryApi.validateCategoryList(productScopeValues);
        }
    }

    @Override
    public CouponTemplateDO getCouponTemplate(Long id) {
        return couponTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<CouponTemplateDO> getCouponTemplatePage(CouponTemplatePageReqVO pageReqVO) {
        return couponTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public void updateCouponTemplateTakeCount(Long id, int incrCount) {
        couponTemplateMapper.updateTakeCount(id, incrCount);
    }

    @Override
    public List<CouponTemplateDO> getCouponTemplateListByTakeType(CouponTakeTypeEnum takeType) {
        return couponTemplateMapper.selectListByTakeType(takeType.getValue());
    }

    @Override
    public List<CouponTemplateDO> getCouponTemplateList(List<Integer> canTakeTypes, Integer productScope,
                                                        Long productScopeValue, Integer count) {
        return couponTemplateMapper.selectList(canTakeTypes, productScope, productScopeValue, count);
    }

    @Override
    public List<CouponTemplateDO> getCouponTemplateList(Collection<Long> ids) {
        return couponTemplateMapper.selectBatchIds(ids);
    }

}
