package cn.econets.blossom.module.crm.controller.admin.product;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.SetUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.crm.controller.admin.product.vo.product.CrmProductPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.product.vo.product.CrmProductRespVO;
import cn.econets.blossom.module.crm.controller.admin.product.vo.product.CrmProductSaveReqVO;
import cn.econets.blossom.module.crm.convert.product.CrmProductConvert;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductCategoryDO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductDO;
import cn.econets.blossom.module.crm.service.product.CrmProductCategoryService;
import cn.econets.blossom.module.crm.service.product.CrmProductService;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSetByFlatMap;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - CRM Products")
@RestController
@RequestMapping("/crm/product")
@Validated
public class CrmProductController {

    @Resource
    private CrmProductService productService;
    @Resource
    private CrmProductCategoryService productCategoryService;
    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "Create product")
    @PreAuthorize("@ss.hasPermission('crm:product:create')")
    public CommonResult<Long> createProduct(@Valid @RequestBody CrmProductSaveReqVO createReqVO) {
        return success(productService.createProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update product")
    @PreAuthorize("@ss.hasPermission('crm:product:update')")
    public CommonResult<Boolean> updateProduct(@Valid @RequestBody CrmProductSaveReqVO updateReqVO) {
        productService.updateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete product")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:product:delete')")
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get products")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:product:query')")
    public CommonResult<CrmProductRespVO> getProduct(@RequestParam("id") Long id) {
        CrmProductDO product = productService.getProduct(id);
        if (product == null) {
            return success(null);
        }
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                SetUtils.asSet(Long.valueOf(product.getCreator()), product.getOwnerUserId()));
        CrmProductCategoryDO category = productCategoryService.getProductCategory(product.getCategoryId());
        return success(CrmProductConvert.INSTANCE.convert(product, userMap, category));
    }

    @GetMapping("/page")
    @Operation(summary = "Get product paging")
    @PreAuthorize("@ss.hasPermission('crm:product:query')")
    public CommonResult<PageResult<CrmProductRespVO>> getProductPage(@Valid CrmProductPageReqVO pageVO) {
        PageResult<CrmProductDO> pageResult = productService.getProductPage(pageVO, getLoginUserId());
        return success(new PageResult<>(getProductDetailList(pageResult.getList()), pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export products Excel")
    @PreAuthorize("@ss.hasPermission('crm:product:export')")
    @OperateLog(type = EXPORT)
    public void exportProductExcel(@Valid CrmProductPageReqVO exportReqVO,
                                   HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CrmProductDO> list = productService.getProductPage(exportReqVO, getLoginUserId()).getList();
        // Export Excel
        ExcelUtils.write(response, "Products.xls", "Data", CrmProductRespVO.class,
                getProductDetailList(list));
    }

    private List<CrmProductRespVO> getProductDetailList(List<CrmProductDO> list) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSetByFlatMap(list, user -> Stream.of(Long.valueOf(user.getCreator()), user.getOwnerUserId())));
        List<CrmProductCategoryDO> productCategoryList = productCategoryService.getProductCategoryList(
                convertSet(list, CrmProductDO::getCategoryId));
        return CrmProductConvert.INSTANCE.convertList(list, userMap, productCategoryList);
    }

}
