package cn.econets.blossom.module.product.api.category;

import cn.econets.blossom.module.product.service.category.ProductCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Product Categories API Interface implementation class
 *
 */
@Service
@Validated
public class ProductCategoryApiImpl implements ProductCategoryApi {

    @Resource
    private ProductCategoryService productCategoryService;

    @Override
    public void validateCategoryList(Collection<Long> ids) {
        productCategoryService.validateCategoryList(ids);
    }

}
