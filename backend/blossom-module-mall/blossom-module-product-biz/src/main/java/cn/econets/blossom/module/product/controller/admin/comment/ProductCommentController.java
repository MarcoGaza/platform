package cn.econets.blossom.module.product.controller.admin.comment;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.controller.admin.comment.vo.*;
import cn.econets.blossom.module.product.dal.dataobject.comment.ProductCommentDO;
import cn.econets.blossom.module.product.service.comment.ProductCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - Product Reviews")
@RestController
@RequestMapping("/product/comment")
@Validated
public class ProductCommentController {

    @Resource
    private ProductCommentService productCommentService;

    @GetMapping("/page")
    @Operation(summary = "Get product review pages")
    @PreAuthorize("@ss.hasPermission('product:comment:query')")
    public CommonResult<PageResult<ProductCommentRespVO>> getCommentPage(@Valid ProductCommentPageReqVO pageVO) {
        PageResult<ProductCommentDO> pageResult = productCommentService.getCommentPage(pageVO);
        return success(BeanUtils.toBean(pageResult, ProductCommentRespVO.class));
    }

    @PutMapping("/update-visible")
    @Operation(summary = "Show / Hide comments")
    @PreAuthorize("@ss.hasPermission('product:comment:update')")
    public CommonResult<Boolean> updateCommentVisible(@Valid @RequestBody ProductCommentUpdateVisibleReqVO updateReqVO) {
        productCommentService.updateCommentVisible(updateReqVO);
        return success(true);
    }

    @PutMapping("/reply")
    @Operation(summary = "Merchant reply")
    @PreAuthorize("@ss.hasPermission('product:comment:update')")
    public CommonResult<Boolean> commentReply(@Valid @RequestBody ProductCommentReplyReqVO replyVO) {
        productCommentService.replyComment(replyVO, getLoginUserId());
        return success(true);
    }

    @PostMapping("/create")
    @Operation(summary = "Add self-evaluation")
    @PreAuthorize("@ss.hasPermission('product:comment:update')")
    public CommonResult<Boolean> createComment(@Valid @RequestBody ProductCommentCreateReqVO createReqVO) {
        productCommentService.createComment(createReqVO);
        return success(true);
    }

}
