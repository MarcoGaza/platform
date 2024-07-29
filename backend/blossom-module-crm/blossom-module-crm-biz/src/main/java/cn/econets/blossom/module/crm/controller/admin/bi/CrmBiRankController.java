package cn.econets.blossom.module.crm.controller.admin.bi;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRanKRespVO;
import cn.econets.blossom.module.crm.controller.admin.bi.vo.CrmBiRankReqVO;
import cn.econets.blossom.module.crm.service.bi.CrmBiRankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;


@Tag(name = "Management Backend - CRM BI Rankings")
@RestController
@RequestMapping("/crm/bi-rank")
@Validated
public class CrmBiRankController {

    @Resource
    private CrmBiRankingService rankingService;

    @GetMapping("/get-contract-price-rank")
    @Operation(summary = "Get the contract amount ranking")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getContractPriceRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getContractPriceRank(rankingReqVO));
    }

    @GetMapping("/get-receivable-price-rank")
    @Operation(summary = "Get the ranking of repayment amount")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getReceivablePriceRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getReceivablePriceRank(rankingReqVO));
    }

    @GetMapping("/get-contract-count-rank")
    @Operation(summary = "Get the ranking of the number of signed contracts")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getContractCountRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getContractCountRank(rankingReqVO));
    }

    @GetMapping("/get-product-sales-rank")
    @Operation(summary = "Get product sales rankings")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getProductSalesRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getProductSalesRank(rankingReqVO));
    }

    @GetMapping("/get-customer-count-rank")
    @Operation(summary = "Get the ranking of new customers")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getCustomerCountRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getCustomerCountRank(rankingReqVO));
    }

    @GetMapping("/get-contacts-count-rank")
    @Operation(summary = "Get the ranking of newly added contacts")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getContactsCountRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getContactsCountRank(rankingReqVO));
    }

    @GetMapping("/get-follow-count-rank")
    @Operation(summary = "Get the follow-up ranking")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getFollowCountRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getFollowCountRank(rankingReqVO));
    }

    @GetMapping("/get-follow-customer-count-rank")
    @Operation(summary = "Get the ranking of the number of follow-up customers")
    @PreAuthorize("@ss.hasPermission('crm:bi-rank:query')")
    public CommonResult<List<CrmBiRanKRespVO>> getFollowCustomerCountRank(@Valid CrmBiRankReqVO rankingReqVO) {
        return success(rankingService.getFollowCustomerCountRank(rankingReqVO));
    }

}
