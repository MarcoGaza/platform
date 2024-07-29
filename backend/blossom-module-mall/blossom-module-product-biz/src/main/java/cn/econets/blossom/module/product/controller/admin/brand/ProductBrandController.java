package cn.econets.blossom.module.product.controller.admin.brand;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.brand.vo.*;
import cn.econets.blossom.module.product.convert.brand.ProductBrandConvert;
import cn.econets.blossom.module.product.dal.dataobject.brand.ProductBrandDO;
import cn.econets.blossom.module.product.service.brand.ProductBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Product Brand")
@RestController
@RequestMapping("/product/brand")
@Validated
public class ProductBrandController {

    @Resource
    private ProductBrandService brandService;

    @PostMapping("/create")
    @Operation(summary = "Create a brand")
    @PreAuthorize("@ss.hasPermission('product:brand:create')")
    public CommonResult<Long> createBrand(@Valid @RequestBody ProductBrandCreateReqVO createReqVO) {
        return success(brandService.createBrand(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update brand")
    @PreAuthorize("@ss.hasPermission('product:brand:update')")
    public CommonResult<Boolean> updateBrand(@Valid @RequestBody ProductBrandUpdateReqVO updateReqVO) {
        brandService.updateBrand(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete brand")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:brand:delete')")
    public CommonResult<Boolean> deleteBrand(@RequestParam("id") Long id) {
        brandService.deleteBrand(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the brand")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:brand:query')")
    public CommonResult<ProductBrandRespVO> getBrand(@RequestParam("id") Long id) {
        ProductBrandDO brand = brandService.getBrand(id);
        return success(ProductBrandConvert.INSTANCE.convert(brand));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a list of simplified brand information", description = "Mainly used for front-end drop-down options")
    public CommonResult<List<ProductBrandSimpleRespVO>> getSimpleBrandList() {
        // Get brand list，As long as it is turned on
        List<ProductBrandDO> list = brandService.getBrandListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // After sorting，Return to the front end
        return success(ProductBrandConvert.INSTANCE.convertList1(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get brand paging")
    @PreAuthorize("@ss.hasPermission('product:brand:query')")
    public CommonResult<PageResult<ProductBrandRespVO>> getBrandPage(@Valid ProductBrandPageReqVO pageVO) {
        PageResult<ProductBrandDO> pageResult = brandService.getBrandPage(pageVO);
        return success(ProductBrandConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list")
    @Operation(summary = "Get brand list")
    @PreAuthorize("@ss.hasPermission('product:brand:query')")
    public CommonResult<List<ProductBrandRespVO>> getBrandList(@Valid ProductBrandListReqVO listVO) {
        List<ProductBrandDO> list = brandService.getBrandList(listVO);
        list.sort(Comparator.comparing(ProductBrandDO::getSort));
        return success(ProductBrandConvert.INSTANCE.convertList(list));
    }

}
