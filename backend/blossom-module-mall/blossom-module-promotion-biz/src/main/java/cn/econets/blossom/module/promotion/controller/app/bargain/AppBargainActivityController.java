package cn.econets.blossom.module.promotion.controller.app.bargain;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.activity.AppBargainActivityDetailRespVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.activity.AppBargainActivityRespVO;
import cn.econets.blossom.module.promotion.convert.bargain.BargainActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import cn.econets.blossom.module.promotion.enums.bargain.BargainRecordStatusEnum;
import cn.econets.blossom.module.promotion.service.bargain.BargainActivityService;
import cn.econets.blossom.module.promotion.service.bargain.BargainRecordService;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "User App - Bargaining activity")
@RestController
@RequestMapping("/promotion/bargain-activity")
@Validated
public class AppBargainActivityController {

    /**
     * {@link AppBargainActivityRespVO} Cache，Refresh asynchronously through it {@link #getBargainActivityList0(Integer)} The home page data you want
     */
    private final LoadingCache<Integer, List<AppBargainActivityRespVO>> bargainActivityListCache = buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<Integer, List<AppBargainActivityRespVO>>() {

                @Override
                public List<AppBargainActivityRespVO> load(Integer count) {
                    return getBargainActivityList0(count);
                }

            });

    @Resource
    private BargainActivityService bargainActivityService;
    @Resource
    private BargainRecordService bargainRecordService;

    @Resource
    private ProductSpuApi spuApi;

    @GetMapping("/list")
    @Operation(summary = "Get the list of bargaining activities", description = "For the mini program homepage")
    @Parameter(name = "count", description = "Number to be displayed", example = "6")
    public CommonResult<List<AppBargainActivityRespVO>> getBargainActivityList(
            @RequestParam(name = "count", defaultValue = "6") Integer count) {
        return success(bargainActivityListCache.getUnchecked(count));
    }

    private List<AppBargainActivityRespVO>getBargainActivityList0(Integer count) {
        List<BargainActivityDO> list = bargainActivityService.getBargainActivityListByCount(count);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        // Splicing data
        List<ProductSpuRespDTO> spuList = spuApi.getSpuList(convertList(list, BargainActivityDO::getSpuId));
        return BargainActivityConvert.INSTANCE.convertAppList(list, spuList);
    }

    @GetMapping("/page")
    @Operation(summary = "Get the bargaining activity page")
    public CommonResult<PageResult<AppBargainActivityRespVO>> getBargainActivityPage(PageParam pageReqVO) {
        PageResult<BargainActivityDO> result = bargainActivityService.getBargainActivityPage(pageReqVO);
        if (CollUtil.isEmpty(result.getList())) {
            return success(PageResult.empty(result.getTotal()));
        }
        // Splicing data
        List<ProductSpuRespDTO> spuList = spuApi.getSpuList(convertList(result.getList(), BargainActivityDO::getSpuId));
        return success(BargainActivityConvert.INSTANCE.convertAppPage(result, spuList));
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get bargaining activity details")
    @Parameter(name = "id", description = "Activity number", example = "1")
    public CommonResult<AppBargainActivityDetailRespVO> getBargainActivityDetail(@RequestParam("id") Long id) {
        BargainActivityDO activity = bargainActivityService.getBargainActivity(id);
        if (activity == null) {
            return success(null);
        }
        // Splicing data
        Integer successUserCount = bargainRecordService.getBargainRecordUserCount(id, BargainRecordStatusEnum.SUCCESS.getStatus());
        ProductSpuRespDTO spu = spuApi.getSpu(activity.getSpuId());
        return success(BargainActivityConvert.INSTANCE.convert(activity, successUserCount, spu));
    }

}
