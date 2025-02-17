package cn.econets.blossom.module.crm.controller.admin.product;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.product.vo.category.CrmProductCategoryCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.product.vo.category.CrmProductCategoryListReqVO;
import cn.econets.blossom.module.crm.controller.admin.product.vo.category.CrmProductCategoryRespVO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductCategoryDO;
import cn.econets.blossom.module.crm.service.product.CrmProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - CRM Product Category")
@RestController
@RequestMapping("/crm/product-category")
@Validated
public class CrmProductCategoryController {

    @Resource
    private CrmProductCategoryService productCategoryService;

    @PostMapping("/create")
    @Operation(summary = "Create product categories")
    @PreAuthorize("@ss.hasPermission('crm:product-category:create')")
    public CommonResult<Long> createProductCategory(@Valid @RequestBody CrmProductCategoryCreateReqVO createReqVO) {
        return success(productCategoryService.createProductCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update product categories")
    @PreAuthorize("@ss.hasPermission('crm:product-category:update')")
    public CommonResult<Boolean> updateProductCategory(@Valid @RequestBody CrmProductCategoryCreateReqVO updateReqVO) {
        productCategoryService.updateProductCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete product category")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:product-category:delete')")
    public CommonResult<Boolean> deleteProductCategory(@RequestParam("id") Long id) {
        productCategoryService.deleteProductCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get product categories")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:product-category:query')")
    public CommonResult<CrmProductCategoryRespVO> getProductCategory(@RequestParam("id") Long id) {
        CrmProductCategoryDO category = productCategoryService.getProductCategory(id);
        return success(BeanUtils.toBean(category, CrmProductCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "Get product category list")
    @PreAuthorize("@ss.hasPermission('crm:product-category:query')")
    public CommonResult<List<CrmProductCategoryRespVO>> getProductCategoryList(@Valid CrmProductCategoryListReqVO listReqVO) {
        List<CrmProductCategoryDO> list = productCategoryService.getProductCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, CrmProductCategoryRespVO.class));
    }

}
