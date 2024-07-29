package cn.econets.blossom.module.product.service.comment;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.api.comment.dto.ProductCommentCreateReqDTO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentCreateReqVO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentPageReqVO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentReplyReqVO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentUpdateVisibleReqVO;
import cn.econets.blossom.module.product.controller.app.comment.vo.AppCommentPageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.comment.ProductCommentDO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Product Reviews Service Interface
 *
 */
@Service
@Validated
public interface ProductCommentService {

    /**
     * Create product review
     * Backend administrators create comments for use
     *
     * @param createReqVO Product review creation Request VO Object
     */
    void createComment(ProductCommentCreateReqVO createReqVO);

    /**
     * Create comment
     * Create product review APP Use to create product reviews on the client
     *
     * @param createReqDTO Create request dto
     * @return Return to comments id
     */
    Long createComment(ProductCommentCreateReqDTO createReqDTO);

    /**
     * Change whether comments are visible
     *
     * @param updateReqVO Modify comments to be visible
     */
    void updateCommentVisible(ProductCommentUpdateVisibleReqVO updateReqVO);

    /**
     * Merchant reply
     *
     * @param replyVO     Merchant reply
     * @param userId Manage the backend merchant login person ID
     */
    void replyComment(ProductCommentReplyReqVO replyVO, Long userId);

    /**
     * 【Administrator】Get product review pages
     *
     * @param pageReqVO Paged query
     * @return Product Reviews Pagination
     */
    PageResult<ProductCommentDO> getCommentPage(ProductCommentPageReqVO pageReqVO);

    /**
     * 【Member】Get product review pages
     *
     * @param pageVO  Paged query
     * @param visible Is it visible?
     * @return Product Reviews Pagination
     */
    PageResult<ProductCommentDO> getCommentPage(AppCommentPageReqVO pageVO, Boolean visible);

}
