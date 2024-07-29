package cn.econets.blossom.module.promotion.controller.app.combination;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.activity.AppCombinationActivityDetailRespVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.activity.AppCombinationActivityRespVO;
import cn.econets.blossom.module.promotion.convert.combination.CombinationActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;
import cn.econets.blossom.module.promotion.service.combination.CombinationActivityService;
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

@Tag(name = "User APP - Group buying activity")
@RestController
@RequestMapping("/promotion/combination-activity")
@Validated
public class AppCombinationActivityController {

    /**
     * {@link AppCombinationActivityRespVO} Cache，Refresh asynchronously through it {@link #getCombinationActivityList0(Integer)} The home page data you want
     */
    private final LoadingCache<Integer, List<AppCombinationActivityRespVO>> combinationActivityListCache = buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<Integer, List<AppCombinationActivityRespVO>>() {

                @Override
                public List<AppCombinationActivityRespVO> load(Integer count) {
                    return getCombinationActivityList0(count);
                }

            });

    @Resource
    private CombinationActivityService activityService;

    @Resource
    private ProductSpuApi spuApi;

    @GetMapping("/list")
    @Operation(summary = "Get the list of group buying activities", description = "For the mini program home page")
    @Parameter(name = "count", description = "Number to be displayed", example = "6")
    public CommonResult<List<AppCombinationActivityRespVO>> getCombinationActivityList(
            @RequestParam(name = "count", defaultValue = "6") Integer count) {
        return success(combinationActivityListCache.getUnchecked(count));
    }

    private List<AppCombinationActivityRespVO> getCombinationActivityList0(Integer count) {
        List<CombinationActivityDO> activityList = activityService.getCombinationActivityListByCount(count);
        if (CollUtil.isEmpty(activityList)) {
            return Collections.emptyList();
        }
        // Splicing returns
        List<CombinationProductDO> productList = activityService.getCombinationProductListByActivityIds(
                convertList(activityList, CombinationActivityDO::getId));
        List<ProductSpuRespDTO> spuList = spuApi.getSpuList(convertList(activityList, CombinationActivityDO::getSpuId));
        return CombinationActivityConvert.INSTANCE.convertAppList(activityList, productList, spuList);
    }

    @GetMapping("/page")
    @Operation(summary = "Get the group buying activity page")
    public CommonResult<PageResult<AppCombinationActivityRespVO>> getCombinationActivityPage(PageParam pageParam) {
        PageResult<CombinationActivityDO> pageResult = activityService.getCombinationActivityPage(pageParam);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }
        // Splicing returns
        List<CombinationProductDO> productList = activityService.getCombinationProductListByActivityIds(
                convertList(pageResult.getList(), CombinationActivityDO::getId));
        List<ProductSpuRespDTO> spuList = spuApi.getSpuList(convertList(pageResult.getList(), CombinationActivityDO::getSpuId));
        return success(CombinationActivityConvert.INSTANCE.convertAppPage(pageResult, productList, spuList));
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get group buying activity details")
    @Parameter(name = "id", description = "Activity number", required = true, example = "1024")
    public CommonResult<AppCombinationActivityDetailRespVO> getCombinationActivityDetail(@RequestParam("id") Long id) {
        // 1. Get activity
        CombinationActivityDO activity = activityService.getCombinationActivity(id);
        if (activity == null
                || ObjectUtil.equal(activity.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            return success(null);
        }

        // 2. Get the event items
        List<CombinationProductDO> products = activityService.getCombinationProductsByActivityId(activity.getId());
        return success(CombinationActivityConvert.INSTANCE.convert3(activity, products));
    }

}
