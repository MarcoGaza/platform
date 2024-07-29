package cn.econets.blossom.module.promotion.controller.admin.combination;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.*;
import cn.econets.blossom.module.promotion.convert.combination.CombinationActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationRecordDO;
import cn.econets.blossom.module.promotion.enums.combination.CombinationRecordStatusEnum;
import cn.econets.blossom.module.promotion.service.combination.CombinationActivityService;
import cn.econets.blossom.module.promotion.service.combination.CombinationRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.hutool.core.collection.CollectionUtil.newArrayList;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "Management Backend - Group buying activity")
@RestController
@RequestMapping("/promotion/combination-activity")
@Validated
public class CombinationActivityController {

    @Resource
    private CombinationActivityService combinationActivityService;
    @Resource
    private CombinationRecordService combinationRecordService;

    @Resource
    private ProductSpuApi productSpuApi;

    @PostMapping("/create")
    @Operation(summary = "Create a group buying activity")
    @PreAuthorize("@ss.hasPermission('promotion:combination-activity:create')")
    public CommonResult<Long> createCombinationActivity(@Valid @RequestBody CombinationActivityCreateReqVO createReqVO) {
        return success(combinationActivityService.createCombinationActivity(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update group buying activities")
    @PreAuthorize("@ss.hasPermission('promotion:combination-activity:update')")
    public CommonResult<Boolean> updateCombinationActivity(@Valid @RequestBody CombinationActivityUpdateReqVO updateReqVO) {
        combinationActivityService.updateCombinationActivity(updateReqVO);
        return success(true);
    }

    @PutMapping("/close")
    @Operation(summary = "Close group buying activities")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:combination-activity:close')")
    public CommonResult<Boolean> closeCombinationActivity(@RequestParam("id") Long id) {
        combinationActivityService.closeCombinationActivityById(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete group buying activity")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:combination-activity:delete')")
    public CommonResult<Boolean> deleteCombinationActivity(@RequestParam("id") Long id) {
        combinationActivityService.deleteCombinationActivity(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get group buying activity")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:combination-activity:query')")
    public CommonResult<CombinationActivityRespVO> getCombinationActivity(@RequestParam("id") Long id) {
        CombinationActivityDO activity = combinationActivityService.getCombinationActivity(id);
        List<CombinationProductDO> products = combinationActivityService.getCombinationProductListByActivityIds(newArrayList(id));
        return success(CombinationActivityConvert.INSTANCE.convert(activity, products));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the group buying activity page")
    @PreAuthorize("@ss.hasPermission('promotion:combination-activity:query')")
    public CommonResult<PageResult<CombinationActivityPageItemRespVO>> getCombinationActivityPage(
            @Valid CombinationActivityPageReqVO pageVO) {
        // Query group buying activities
        PageResult<CombinationActivityDO> pageResult = combinationActivityService.getCombinationActivityPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // Statistics
        Set<Long> activityIds = convertSet(pageResult.getList(), CombinationActivityDO::getId);
        Map<Long, Integer> groupCountMap = combinationRecordService.getCombinationRecordCountMapByActivity(
                activityIds, null, CombinationRecordDO.HEAD_ID_GROUP);
        Map<Long, Integer> groupSuccessCountMap = combinationRecordService.getCombinationRecordCountMapByActivity(
                activityIds, CombinationRecordStatusEnum.SUCCESS.getStatus(), CombinationRecordDO.HEAD_ID_GROUP);
        Map<Long, Integer> recordCountMap = combinationRecordService.getCombinationRecordCountMapByActivity(
                activityIds, null, null);
        // Splicing data
        List<CombinationProductDO> products = combinationActivityService.getCombinationProductListByActivityIds(
                convertSet(pageResult.getList(), CombinationActivityDO::getId));
        List<ProductSpuRespDTO> spus = productSpuApi.getSpuList(
                convertSet(pageResult.getList(), CombinationActivityDO::getSpuId));
        return success(CombinationActivityConvert.INSTANCE.convertPage(pageResult, products,
                groupCountMap, groupSuccessCountMap, recordCountMap, spus));
    }

}
