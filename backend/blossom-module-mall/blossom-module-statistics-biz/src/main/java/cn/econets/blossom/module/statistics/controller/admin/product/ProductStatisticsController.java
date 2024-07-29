package cn.econets.blossom.module.statistics.controller.admin.product;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.pojo.SortablePageParam;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.product.vo.ProductStatisticsReqVO;
import cn.econets.blossom.module.statistics.controller.admin.product.vo.ProductStatisticsRespVO;
import cn.econets.blossom.module.statistics.dal.dataobject.product.ProductStatisticsDO;
import cn.econets.blossom.module.statistics.service.product.ProductStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "Management Backend - Product Statistics")
@RestController
@RequestMapping("/statistics/product")
@Validated
public class ProductStatisticsController {

    @Resource
    private ProductStatisticsService productStatisticsService;

    @Resource
    private ProductSpuApi productSpuApi;

    @GetMapping("/analyse")
    @Operation(summary = "Get product statistics analysis")
    @PreAuthorize("@ss.hasPermission('statistics:product:query')")
    public CommonResult<DataComparisonRespVO<ProductStatisticsRespVO>> getProductStatisticsAnalyse(ProductStatisticsReqVO reqVO) {
        return success(productStatisticsService.getProductStatisticsAnalyse(reqVO));
    }

    @GetMapping("/list")
    @Operation(summary = "Get product statistics details（Date dimension）")
    @PreAuthorize("@ss.hasPermission('statistics:product:query')")
    public CommonResult<List<ProductStatisticsRespVO>> getProductStatisticsList(ProductStatisticsReqVO reqVO) {
        List<ProductStatisticsDO> list = productStatisticsService.getProductStatisticsList(reqVO);
        return success(BeanUtils.toBean(list, ProductStatisticsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export to obtain product statistics details Excel（Date dimension）")
    @PreAuthorize("@ss.hasPermission('statistics:product:export')")
    public void exportProductStatisticsExcel(ProductStatisticsReqVO reqVO, HttpServletResponse response) throws IOException {
        List<ProductStatisticsDO> list = productStatisticsService.getProductStatisticsList(reqVO);
        // Export Excel
        List<ProductStatisticsRespVO> voList = BeanUtils.toBean(list, ProductStatisticsRespVO.class);
        ExcelUtils.write(response, "Product Status.xls", "Data", ProductStatisticsRespVO.class, voList);
    }

    @GetMapping("/rank-page")
    @Operation(summary = "Get the product statistics ranking page（Product Dimension）")
    @PreAuthorize("@ss.hasPermission('statistics:product:query')")
    public CommonResult<PageResult<ProductStatisticsRespVO>> getProductStatisticsRankPage(@Valid ProductStatisticsReqVO reqVO,
                                                                                          @Valid SortablePageParam pageParam) {
        PageResult<ProductStatisticsDO> pageResult = productStatisticsService.getProductStatisticsRankPage(reqVO, pageParam);
        // Processing product information
        Set<Long> spuIds = convertSet(pageResult.getList(), ProductStatisticsDO::getSpuId);
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(productSpuApi.getSpuList(spuIds), ProductSpuRespDTO::getId);
        return success(BeanUtils.toBean(pageResult, ProductStatisticsRespVO.class,
                item -> Optional.ofNullable(spuMap.get(item.getSpuId()))
                        .ifPresent(spu -> item.setName(spu.getName()).setPicUrl(spu.getPicUrl()))));
    }

}