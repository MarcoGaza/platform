package cn.econets.blossom.module.promotion.controller.app.seckill;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity.AppSeckillActivityDetailRespVO;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity.AppSeckillActivityNowRespVO;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity.AppSeckillActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity.AppSeckillActivityRespVO;
import cn.econets.blossom.module.promotion.convert.seckill.seckillactivity.SeckillActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillConfigDO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillProductDO;
import cn.econets.blossom.module.promotion.service.seckill.SeckillActivityService;
import cn.econets.blossom.module.promotion.service.seckill.SeckillConfigService;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.findFirst;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.isBetween;

@Tag(name = "User App - Second-sale event")
@RestController
@RequestMapping("/promotion/seckill-activity")
@Validated
public class AppSeckillActivityController {

    /**
     * {@link AppSeckillActivityNowRespVO} Cache，Refresh asynchronously through it {@link #getNowSeckillActivity()} The home page data you want
     */
    private final LoadingCache<String, AppSeckillActivityNowRespVO> nowSeckillActivityCache = buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<String, AppSeckillActivityNowRespVO>() {

                @Override
                public AppSeckillActivityNowRespVO load(String key) {
                     return getNowSeckillActivity0();
                }

            });

    @Resource
    private SeckillActivityService activityService;
    @Resource
    @Lazy
    private SeckillConfigService configService;

    @Resource
    private ProductSpuApi spuApi;

    @GetMapping("/get-now")
    @Operation(summary = "Get the current flash sale activity", description = "Get the current activities in progress，Provided for homepage use")
    public CommonResult<AppSeckillActivityNowRespVO> getNowSeckillActivity() {
        return success(nowSeckillActivityCache.getUnchecked("")); // Cache
    }

    private AppSeckillActivityNowRespVO getNowSeckillActivity0() {
        // 1. Get the current time in which flash sale phase
        SeckillConfigDO config = configService.getCurrentSeckillConfig();
        if (config == null) { // Return directly if the time period does not exist null
            return new AppSeckillActivityNowRespVO();
        }

        // 2.1 Query the activities that meet the current stage
        List<SeckillActivityDO> activityList = activityService.getSeckillActivityListByConfigIdAndStatus(config.getId(), CommonStatusEnum.ENABLE.getStatus());
        List<SeckillProductDO> productList = activityService.getSeckillProductListByActivityId(
                convertList(activityList, SeckillActivityDO::getId));
        // 2.2 Get spu Information
        List<ProductSpuRespDTO> spuList = spuApi.getSpuList(convertList(activityList, SeckillActivityDO::getSpuId));
        return SeckillActivityConvert.INSTANCE.convert(config, activityList, productList, spuList);
    }

    @GetMapping("/page")
    @Operation(summary = "Get the flash sale activity page")
    public CommonResult<PageResult<AppSeckillActivityRespVO>> getSeckillActivityPage(AppSeckillActivityPageReqVO pageReqVO) {
        // 1. Query the activities that meet the current stage
        PageResult<SeckillActivityDO> pageResult = activityService.getSeckillActivityAppPageByConfigId(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }
        List<SeckillProductDO> productList = activityService.getSeckillProductListByActivityId(
                convertList(pageResult.getList(), SeckillActivityDO::getId));

        // 2. Splicing data
        List<ProductSpuRespDTO> spuList = spuApi.getSpuList(convertList(pageResult.getList(), SeckillActivityDO::getSpuId));
        return success(SeckillActivityConvert.INSTANCE.convertPage02(pageResult, productList, spuList));
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get the details of the flash sale")
    @Parameter(name = "id", description = "Activity number", required = true, example = "1024")
    public CommonResult<AppSeckillActivityDetailRespVO> getSeckillActivity(@RequestParam("id") Long id) {
        // 1. Get activities
        SeckillActivityDO activity = activityService.getSeckillActivity(id);
        if (activity == null
                || ObjectUtil.equal(activity.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            return success(null);
        }

        // 2. Get time period
        List<SeckillConfigDO> configs = configService.getSeckillConfigListByStatus(CommonStatusEnum.ENABLE.getStatus());
        configs.removeIf(config -> !CollUtil.contains(activity.getConfigIds(), config.getId()));
        // 2.1 Use the current time period first
        SeckillConfigDO config = findFirst(configs, config0 -> isBetween(config0.getStartTime(), config0.getEndTime()));
        // 2.2 If not，Get the last one，Because it tends to be displayed first“Not started yet” > “Ended”
        if (config == null) {
            config = CollUtil.getLast(configs);
        }
        if (config == null) {
            return null;
        }
        // 3. Calculation start time、End time
        LocalDate nowDate;
        // 3.1 If within the event date range，Today is nowDate
        if (LocalDateTimeUtils.isBetween(activity.getStartTime(), activity.getEndTime())) {
            nowDate = LocalDate.now();
        } else {
            // 3.2 If it is not within the activity time range，Directly use the activity endTime As nowDate，Because I prefer to display it first“Not started yet” > “Ended”
            nowDate = activity.getEndTime().toLocalDate();
        }
        LocalDateTime startTime = LocalDateTime.of(nowDate, LocalTime.parse(config.getStartTime()));
        LocalDateTime endTime = LocalDateTime.of(nowDate, LocalTime.parse(config.getEndTime()));

        // 4. Splicing data
        List<SeckillProductDO> productList = activityService.getSeckillProductListByActivityId(activity.getId());
        return success(SeckillActivityConvert.INSTANCE.convert3(activity, productList, startTime, endTime));
    }

}
