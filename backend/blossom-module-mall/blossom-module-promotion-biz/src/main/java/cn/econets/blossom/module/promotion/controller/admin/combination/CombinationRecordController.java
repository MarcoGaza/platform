package cn.econets.blossom.module.promotion.controller.admin.combination;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordPageItemRespVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordReqPageVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordSummaryVO;
import cn.econets.blossom.module.promotion.convert.combination.CombinationActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationRecordDO;
import cn.econets.blossom.module.promotion.enums.combination.CombinationRecordStatusEnum;
import cn.econets.blossom.module.promotion.service.combination.CombinationActivityService;
import cn.econets.blossom.module.promotion.service.combination.CombinationRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "Management Backend - Group buying record")
@RestController
@RequestMapping("/promotion/combination-record")
@Validated
public class CombinationRecordController {

    @Resource
    private CombinationActivityService combinationActivityService;
    @Resource
    @Lazy
    private CombinationRecordService combinationRecordService;

    @GetMapping("/page")
    @Operation(summary = "Get the group purchase record page")
    @PreAuthorize("@ss.hasPermission('promotion:combination-record:query')")
    public CommonResult<PageResult<CombinationRecordPageItemRespVO>> getCombinationRecordPage(
            @Valid CombinationRecordReqPageVO pageVO) {
        PageResult<CombinationRecordDO> recordPage = combinationRecordService.getCombinationRecordPage(pageVO);
        // Splicing data
        List<CombinationActivityDO> activities = combinationActivityService.getCombinationActivityListByIds(
                convertSet(recordPage.getList(), CombinationRecordDO::getActivityId));
        List<CombinationProductDO> products = combinationActivityService.getCombinationProductListByActivityIds(
                convertSet(recordPage.getList(), CombinationRecordDO::getActivityId));
        return success(CombinationActivityConvert.INSTANCE.convert(recordPage, activities, products));
    }

    @GetMapping("/get-summary")
    @Operation(summary = "Get summary information of group purchase records", description = "Used for group purchase record page display")
    @PreAuthorize("@ss.hasPermission('promotion:combination-record:query')")
    public CommonResult<CombinationRecordSummaryVO> getCombinationRecordSummary() {
        CombinationRecordSummaryVO summaryVO = new CombinationRecordSummaryVO();
        summaryVO.setUserCount(combinationRecordService.getCombinationUserCount()); // Get the number of users participating in group buying
        summaryVO.setSuccessCount(combinationRecordService.getCombinationRecordCount( // Get group records
                CombinationRecordStatusEnum.SUCCESS.getStatus(), null, CombinationRecordDO.HEAD_ID_GROUP));
        summaryVO.setVirtualGroupCount(combinationRecordService.getCombinationRecordCount(// Get virtual group records
                null, Boolean.TRUE, CombinationRecordDO.HEAD_ID_GROUP));
        return success(summaryVO);
    }

}
