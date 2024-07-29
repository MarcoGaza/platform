package cn.econets.blossom.module.product.service.brand;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.brand.vo.ProductBrandCreateReqVO;
import cn.econets.blossom.module.product.controller.admin.brand.vo.ProductBrandListReqVO;
import cn.econets.blossom.module.product.controller.admin.brand.vo.ProductBrandPageReqVO;
import cn.econets.blossom.module.product.controller.admin.brand.vo.ProductBrandUpdateReqVO;
import cn.econets.blossom.module.product.convert.brand.ProductBrandConvert;
import cn.econets.blossom.module.product.dal.dataobject.brand.ProductBrandDO;
import cn.econets.blossom.module.product.dal.mysql.brand.ProductBrandMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.*;

/**
 * Brand Service Implementation class
 *
 */
@Service
@Validated
public class ProductBrandServiceImpl implements ProductBrandService {

    @Resource
    private ProductBrandMapper brandMapper;

    @Override
    public Long createBrand(ProductBrandCreateReqVO createReqVO) {
        // Verification
        validateBrandNameUnique(null, createReqVO.getName());

        // Insert
        ProductBrandDO brand = ProductBrandConvert.INSTANCE.convert(createReqVO);
        brandMapper.insert(brand);
        // Return
        return brand.getId();
    }

    @Override
    public void updateBrand(ProductBrandUpdateReqVO updateReqVO) {
        // Check existence
        validateBrandExists(updateReqVO.getId());
        validateBrandNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // Update
        ProductBrandDO updateObj = ProductBrandConvert.INSTANCE.convert(updateReqVO);
        brandMapper.updateById(updateObj);
    }

    @Override
    public void deleteBrand(Long id) {
        // Check existence
        validateBrandExists(id);
        // Delete
        brandMapper.deleteById(id);
    }

    private void validateBrandExists(Long id) {
        if (brandMapper.selectById(id) == null) {
            throw exception(BRAND_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void validateBrandNameUnique(Long id, String name) {
        ProductBrandDO brand = brandMapper.selectByName(name);
        if (brand == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Dictionary type
        if (id == null) {
            throw exception(BRAND_NAME_EXISTS);
        }
        if (!brand.getId().equals(id)) {
            throw exception(BRAND_NAME_EXISTS);
        }
    }

    @Override
    public ProductBrandDO getBrand(Long id) {
        return brandMapper.selectById(id);
    }

    @Override
    public List<ProductBrandDO> getBrandList(Collection<Long> ids) {
        return brandMapper.selectBatchIds(ids);
    }

    @Override
    public List<ProductBrandDO> getBrandList(ProductBrandListReqVO listReqVO) {
        return brandMapper.selectList(listReqVO);
    }

    @Override
    public void validateProductBrand(Long id) {
        ProductBrandDO brand = brandMapper.selectById(id);
        if (brand == null) {
            throw exception(BRAND_NOT_EXISTS);
        }
        if (brand.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(BRAND_DISABLED);
        }
    }

    @Override
    public PageResult<ProductBrandDO> getBrandPage(ProductBrandPageReqVO pageReqVO) {
        return brandMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProductBrandDO> getBrandListByStatus(Integer status) {
        return brandMapper.selectListByStatus(status);
    }

}
