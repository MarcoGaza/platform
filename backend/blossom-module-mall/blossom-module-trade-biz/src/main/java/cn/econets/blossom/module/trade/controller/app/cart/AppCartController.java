package cn.econets.blossom.module.trade.controller.app.cart;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.trade.controller.app.cart.vo.*;
import cn.econets.blossom.module.trade.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User App - Shopping Cart")
@RestController
@RequestMapping("/trade/cart")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AppCartController {

    @Resource
    private CartService cartService;

    @PostMapping("/add")
    @Operation(summary = "Add shopping cart item")
    @PreAuthenticated
    public CommonResult<Long> addCart(@Valid @RequestBody AppCartAddReqVO addCountReqVO) {
        return success(cartService.addCart(getLoginUserId(), addCountReqVO));
    }

    @PutMapping("/update-count")
    @Operation(summary = "Update shopping cart item quantity")
    @PreAuthenticated
    public CommonResult<Boolean> updateCartCount(@Valid @RequestBody AppCartUpdateCountReqVO updateReqVO) {
        cartService.updateCartCount(getLoginUserId(), updateReqVO);
        return success(true);
    }

    @PutMapping("/update-selected")
    @Operation(summary = "Update shopping cart items selected")
    @PreAuthenticated
    public CommonResult<Boolean> updateCartSelected(@Valid @RequestBody AppCartUpdateSelectedReqVO updateReqVO) {
        cartService.updateCartSelected(getLoginUserId(), updateReqVO);
        return success(true);
    }

    @PutMapping("/reset")
    @Operation(summary = "Reset shopping cart items")
    @PreAuthenticated
    public CommonResult<Boolean> resetCart(@Valid @RequestBody AppCartResetReqVO updateReqVO) {
        cartService.resetCart(getLoginUserId(), updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete shopping cart items")
    @Parameter(name = "ids", description = "Shopping cart item number", required = true, example = "1024,2048")
    @PreAuthenticated
    public CommonResult<Boolean> deleteCart(@RequestParam("ids") List<Long> ids) {
        cartService.deleteCart(getLoginUserId(), ids);
        return success(true);
    }

    @GetMapping("get-count")
    @Operation(summary = "Query the number of items in the user's shopping cart")
    @PreAuthenticated
    public CommonResult<Integer> getCartCount() {
        return success(cartService.getCartCount(getLoginUserId()));
    }

    @GetMapping("/list")
    @Operation(summary = "Query the user's shopping cart list")
    @PreAuthenticated
    public CommonResult<AppCartListRespVO> getCartList() {
        return success(cartService.getCartList(getLoginUserId()));
    }

}
