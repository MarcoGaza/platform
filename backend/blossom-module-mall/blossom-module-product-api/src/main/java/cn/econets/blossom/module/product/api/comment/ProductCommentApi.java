package cn.econets.blossom.module.product.api.comment;

import cn.econets.blossom.module.product.api.comment.dto.ProductCommentCreateReqDTO;

/**
 * Product Reviews API Interface
 *
 */
public interface ProductCommentApi {

    /**
     * Create comment
     *
     * @param createReqDTO Comment parameters
     * @return Return the comment created id
     */
    Long createComment(ProductCommentCreateReqDTO createReqDTO);

}
