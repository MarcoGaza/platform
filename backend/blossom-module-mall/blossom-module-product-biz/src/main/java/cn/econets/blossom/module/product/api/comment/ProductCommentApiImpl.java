package cn.econets.blossom.module.product.api.comment;

import cn.econets.blossom.module.product.api.comment.dto.ProductCommentCreateReqDTO;
import cn.econets.blossom.module.product.service.comment.ProductCommentService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * Product Reviews API Implementation class
 *
 */
@Service
@Validated
public class ProductCommentApiImpl implements ProductCommentApi {

    @Resource
    private ProductCommentService productCommentService;

    @Override
    public Long createComment(ProductCommentCreateReqDTO createReqDTO) {
        return productCommentService.createComment(createReqDTO);
    }

}
