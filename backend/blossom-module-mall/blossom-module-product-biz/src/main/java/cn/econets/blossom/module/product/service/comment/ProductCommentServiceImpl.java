package cn.econets.blossom.module.product.service.comment;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.product.api.comment.dto.ProductCommentCreateReqDTO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentCreateReqVO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentPageReqVO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentReplyReqVO;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentUpdateVisibleReqVO;
import cn.econets.blossom.module.product.controller.app.comment.vo.AppCommentPageReqVO;
import cn.econets.blossom.module.product.convert.comment.ProductCommentConvert;
import cn.econets.blossom.module.product.dal.dataobject.comment.ProductCommentDO;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.econets.blossom.module.product.dal.mysql.comment.ProductCommentMapper;
import cn.econets.blossom.module.product.service.sku.ProductSkuService;
import cn.econets.blossom.module.product.service.spu.ProductSpuService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.*;

/**
 * Product Reviews Service Implementation class
 *
 */
@Service
@Validated
public class ProductCommentServiceImpl implements ProductCommentService {

    @Resource
    private ProductCommentMapper productCommentMapper;

    @Resource
    private ProductSpuService productSpuService;

    @Resource
    @Lazy
    private ProductSkuService productSkuService;

    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public void createComment(ProductCommentCreateReqVO createReqVO) {
        // Verification SKU
        ProductSkuDO sku = validateSku(createReqVO.getSkuId());
        // Verification SPU
        ProductSpuDO spu = validateSpu(sku.getSpuId());

        // Create comment
        ProductCommentDO comment = ProductCommentConvert.INSTANCE.convert(createReqVO, spu, sku);
        productCommentMapper.insert(comment);
    }

    @Override
    public Long createComment(ProductCommentCreateReqDTO createReqDTO) {
        // Verification SKU
        ProductSkuDO sku = validateSku(createReqDTO.getSkuId());
        // Verification SPU
        ProductSpuDO spu = validateSpu(sku.getSpuId());
        // Check comments
        validateCommentExists(createReqDTO.getUserId(), createReqDTO.getOrderId());
        // Get user details
        MemberUserRespDTO user = memberUserApi.getUser(createReqDTO.getUserId());

        // Create comment
        ProductCommentDO comment = ProductCommentConvert.INSTANCE.convert(createReqDTO, spu, sku, user);
        productCommentMapper.insert(comment);
        return comment.getId();
    }

    /**
     * Determine whether the current product of the current order has been evaluated by the user
     *
     * @param userId      User Number
     * @param orderItemId Order Item Number
     */
    private void validateCommentExists(Long userId, Long orderItemId) {
        ProductCommentDO exist = productCommentMapper.selectByUserIdAndOrderItemId(userId, orderItemId);
        if (exist != null) {
            throw exception(COMMENT_ORDER_EXISTS);
        }
    }

    private ProductSkuDO validateSku(Long skuId) {
        ProductSkuDO sku = productSkuService.getSku(skuId);
        if (sku == null) {
            throw exception(SKU_NOT_EXISTS);
        }
        return sku;
    }

    private ProductSpuDO validateSpu(Long spuId) {
        ProductSpuDO spu = productSpuService.getSpu(spuId);
        if (null == spu) {
            throw exception(SPU_NOT_EXISTS);
        }
        return spu;
    }

    @Override
    public void updateCommentVisible(ProductCommentUpdateVisibleReqVO updateReqVO) {
        // Check if the comment exists
        validateCommentExists(updateReqVO.getId());

        // Update visible status
        productCommentMapper.updateById(new ProductCommentDO().setId(updateReqVO.getId())
                .setVisible(true));
    }

    @Override
    public void replyComment(ProductCommentReplyReqVO replyVO, Long userId) {
        // Check if the comment exists
        validateCommentExists(replyVO.getId());
        // Reply to comment
        productCommentMapper.updateById(new ProductCommentDO().setId(replyVO.getId())
                .setReplyTime(LocalDateTime.now()).setReplyUserId(userId)
                .setReplyStatus(Boolean.TRUE).setReplyContent(replyVO.getReplyContent()));
    }

    private ProductCommentDO validateCommentExists(Long id) {
        ProductCommentDO productComment = productCommentMapper.selectById(id);
        if (productComment == null) {
            throw exception(COMMENT_NOT_EXISTS);
        }
        return productComment;
    }

    @Override
    public PageResult<ProductCommentDO> getCommentPage(AppCommentPageReqVO pageVO, Boolean visible) {
        return productCommentMapper.selectPage(pageVO, visible);
    }

    @Override
    public PageResult<ProductCommentDO> getCommentPage(ProductCommentPageReqVO pageReqVO) {
        return productCommentMapper.selectPage(pageReqVO);
    }

}
