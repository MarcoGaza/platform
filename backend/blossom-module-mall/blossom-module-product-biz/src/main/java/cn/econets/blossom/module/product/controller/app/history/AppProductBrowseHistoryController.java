package cn.econets.blossom.module.product.controller.app.history;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.product.controller.admin.history.vo.ProductBrowseHistoryPageReqVO;
import cn.econets.blossom.module.product.controller.app.history.vo.AppProductBrowseHistoryDeleteReqVO;
import cn.econets.blossom.module.product.controller.app.history.vo.AppProductBrowseHistoryPageReqVO;
import cn.econets.blossom.module.product.controller.app.history.vo.AppProductBrowseHistoryRespVO;
import cn.econets.blossom.module.product.dal.dataobject.history.ProductBrowseHistoryDO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.econets.blossom.module.product.service.history.ProductBrowseHistoryService;
import cn.econets.blossom.module.product.service.spu.ProductSpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User APP - Product browsing history")
@RestController
@RequestMapping("/product/browse-history")
public class AppProductBrowseHistoryController {

    @Resource
    private ProductBrowseHistoryService productBrowseHistoryService;
    @Resource
    private ProductSpuService productSpuService;

    @DeleteMapping(value = "/delete")
    @Operation(summary = "Delete product browsing history")
    @PreAuthenticated
    public CommonResult<Boolean> deleteBrowseHistory(@RequestBody @Valid AppProductBrowseHistoryDeleteReqVO reqVO) {
        productBrowseHistoryService.hideUserBrowseHistory(getLoginUserId(), reqVO.getSpuIds());
        return success(Boolean.TRUE);
    }

    @DeleteMapping(value = "/clean")
    @Operation(summary = "Clear product browsing history")
    @PreAuthenticated
    public CommonResult<Boolean> deleteBrowseHistory() {
        productBrowseHistoryService.hideUserBrowseHistory(getLoginUserId(), null);
        return success(Boolean.TRUE);
    }

    @GetMapping(value = "/page")
    @Operation(summary = "Get product browsing history page")
    @PreAuthenticated
    public CommonResult<PageResult<AppProductBrowseHistoryRespVO>> getBrowseHistoryPage(AppProductBrowseHistoryPageReqVO reqVO) {
        ProductBrowseHistoryPageReqVO pageReqVO = BeanUtils.toBean(reqVO, ProductBrowseHistoryPageReqVO.class)
                .setUserId(getLoginUserId())
                .setUserDeleted(false); // Exclude users who have deleted them（Hidden）
        PageResult<ProductBrowseHistoryDO> pageResult = productBrowseHistoryService.getBrowseHistoryPage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }

        // Get the product spu Information
        Set<Long> spuIds = convertSet(pageResult.getList(), ProductBrowseHistoryDO::getSpuId);
        Map<Long, ProductSpuDO> spuMap = convertMap(productSpuService.getSpuList(spuIds), ProductSpuDO::getId);
        return success(BeanUtils.toBean(pageResult, AppProductBrowseHistoryRespVO.class,
                vo -> Optional.ofNullable(spuMap.get(vo.getSpuId()))
                        .ifPresent(spu -> vo.setSpuName(spu.getName()).setPicUrl(spu.getPicUrl()).setPrice(spu.getPrice()))));
    }

}
