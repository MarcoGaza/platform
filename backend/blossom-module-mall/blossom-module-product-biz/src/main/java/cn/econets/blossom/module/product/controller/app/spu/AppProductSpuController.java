package cn.econets.blossom.module.product.controller.app.spu;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.member.api.level.MemberLevelApi;
import cn.econets.blossom.module.member.api.level.dto.MemberLevelRespDTO;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.product.controller.app.spu.vo.AppProductSpuDetailRespVO;
import cn.econets.blossom.module.product.controller.app.spu.vo.AppProductSpuPageReqVO;
import cn.econets.blossom.module.product.controller.app.spu.vo.AppProductSpuRespVO;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.econets.blossom.module.product.enums.spu.ProductSpuStatusEnum;
import cn.econets.blossom.module.product.service.history.ProductBrowseHistoryService;
import cn.econets.blossom.module.product.service.sku.ProductSkuService;
import cn.econets.blossom.module.product.service.spu.ProductSpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SPU_NOT_ENABLE;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SPU_NOT_EXISTS;

@Tag(name = "User APP - Products SPU")
@RestController
@RequestMapping("/product/spu")
@Validated
public class AppProductSpuController {

    @Resource
    private ProductSpuService productSpuService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ProductBrowseHistoryService productBrowseHistoryService;

    @Resource
    private MemberLevelApi memberLevelApi;
    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/list-by-ids")
    @Operation(summary = "Get the product SPU List")
    @Parameter(name = "ids", description = "Numbered list", required = true)
    public CommonResult<List<AppProductSpuRespVO>> getSpuList(@RequestParam("ids") Set<Long> ids) {
        List<ProductSpuDO> list = productSpuService.getSpuList(ids);
        if (CollUtil.isEmpty(list)) {
            return success(Collections.emptyList());
        }

        // Splicing returns
        list.forEach(spu -> spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount()));
        List<AppProductSpuRespVO> voList = BeanUtils.toBean(list, AppProductSpuRespVO.class);
        // Processing vip Price
        MemberLevelRespDTO memberLevel = getMemberLevel();
        voList.forEach(vo -> vo.setVipPrice(calculateVipPrice(vo.getPrice(), memberLevel)));
        return success(voList);
    }

    @GetMapping("/page")
    @Operation(summary = "Get goods SPU Pagination")
    public CommonResult<PageResult<AppProductSpuRespVO>> getSpuPage(@Valid AppProductSpuPageReqVO pageVO) {
        PageResult<ProductSpuDO> pageResult = productSpuService.getSpuPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // Splicing returns
        pageResult.getList().forEach(spu -> spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount()));
        PageResult<AppProductSpuRespVO> voPageResult = BeanUtils.toBean(pageResult, AppProductSpuRespVO.class);
        // Processing vip Price
        MemberLevelRespDTO memberLevel = getMemberLevel();
        voPageResult.getList().forEach(vo -> vo.setVipPrice(calculateVipPrice(vo.getPrice(), memberLevel)));
        return success(voPageResult);
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get goods SPU Details")
    @Parameter(name = "id", description = "Number", required = true)
    public CommonResult<AppProductSpuDetailRespVO> getSpuDetail(@RequestParam("id") Long id) {
        // Get goods SPU
        ProductSpuDO spu = productSpuService.getSpu(id);
        if (spu == null) {
            throw exception(SPU_NOT_EXISTS);
        }
        if (!ProductSpuStatusEnum.isEnable(spu.getStatus())) {
            throw exception(SPU_NOT_ENABLE);
        }
        // Get goods SKU
        List<ProductSkuDO> skus = productSkuService.getSkuListBySpuId(spu.getId());

        // Increase views
        productSpuService.updateBrowseCount(id, 1);
        // Save browsing history
        productBrowseHistoryService.createBrowseHistory(getLoginUserId(), id);

        // Splicing returns
        spu.setBrowseCount(spu.getBrowseCount() + spu.getVirtualSalesCount());
        AppProductSpuDetailRespVO spuVO = BeanUtils.toBean(spu, AppProductSpuDetailRespVO.class)
                .setSkus(BeanUtils.toBean(skus, AppProductSpuDetailRespVO.Sku.class));
        // Processing vip Price
        MemberLevelRespDTO memberLevel = getMemberLevel();
        spuVO.setVipPrice(calculateVipPrice(spuVO.getPrice(), memberLevel));
        return success(spuVO);
    }

    private MemberLevelRespDTO getMemberLevel() {
        Long userId = getLoginUserId();
        if (userId == null) {
            return null;
        }
        MemberUserRespDTO user = memberUserApi.getUser(userId);
        if (user.getLevelId() == null || user.getLevelId() <= 0) {
            return null;
        }
        return memberLevelApi.getMemberLevel(user.getLevelId());
    }

    /**
     * Calculate Membership VIP Preferential price
     *
     * @param price Original price
     * @param memberLevel Member Level
     * @return Preferential price
     */
    public Integer calculateVipPrice(Integer price, MemberLevelRespDTO memberLevel) {
        if (memberLevel == null || memberLevel.getDiscountPercent() == null) {
            return 0;
        }
        Integer newPrice = price * memberLevel.getDiscountPercent() / 100;
        return price - newPrice;
    }

    // TODO Product browsing history；
}
