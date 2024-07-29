package cn.econets.blossom.module.product.controller.admin.property;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.controller.admin.property.vo.property.ProductPropertyPageReqVO;
import cn.econets.blossom.module.product.controller.admin.property.vo.property.ProductPropertyRespVO;
import cn.econets.blossom.module.product.controller.admin.property.vo.property.ProductPropertySaveReqVO;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyDO;
import cn.econets.blossom.module.product.service.property.ProductPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Product attribute item")
@RestController
@RequestMapping("/product/property")
@Validated
public class ProductPropertyController {

    @Resource
    private ProductPropertyService productPropertyService;

    @PostMapping("/create")
    @Operation(summary = "Create property item")
    @PreAuthorize("@ss.hasPermission('product:property:create')")
    public CommonResult<Long> createProperty(@Valid @RequestBody ProductPropertySaveReqVO createReqVO) {
        return success(productPropertyService.createProperty(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update property items")
    @PreAuthorize("@ss.hasPermission('product:property:update')")
    public CommonResult<Boolean> updateProperty(@Valid @RequestBody ProductPropertySaveReqVO updateReqVO) {
        productPropertyService.updateProperty(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete attribute item")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('product:property:delete')")
    public CommonResult<Boolean> deleteProperty(@RequestParam("id") Long id) {
        productPropertyService.deleteProperty(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get property items")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:property:query')")
    public CommonResult<ProductPropertyRespVO> getProperty(@RequestParam("id") Long id) {
        ProductPropertyDO property = productPropertyService.getProperty(id);
        return success(BeanUtils.toBean(property, ProductPropertyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get property item paging")
    @PreAuthorize("@ss.hasPermission('product:property:query')")
    public CommonResult<PageResult<ProductPropertyRespVO>> getPropertyPage(@Valid ProductPropertyPageReqVO pageVO) {
        PageResult<ProductPropertyDO> pageResult = productPropertyService.getPropertyPage(pageVO);
        return success(BeanUtils.toBean(pageResult, ProductPropertyRespVO.class));
    }

}
