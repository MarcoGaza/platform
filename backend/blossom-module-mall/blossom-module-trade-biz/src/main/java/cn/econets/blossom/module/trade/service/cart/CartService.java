package cn.econets.blossom.module.trade.service.cart;

import cn.econets.blossom.module.trade.controller.app.cart.vo.*;
import cn.econets.blossom.module.trade.dal.dataobject.cart.CartDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Shopping Cart Service Interface
 *
 */
public interface CartService {

    /**
     * Add product to shopping cart
     *
     * @param userId User Number
     * @param addReqVO Add information
     * @return The number of the shopping item
     */
    Long addCart(Long userId, @Valid AppCartAddReqVO addReqVO);

    /**
     * Update shopping cart item quantity
     *
     * @param userId User Number
     * @param updateCountReqVO Update information
     */
    void updateCartCount(Long userId, AppCartUpdateCountReqVO updateCountReqVO);

    /**
     * Update shopping cart selection status
     *
     * @param userId User Number
     * @param updateSelectedReqVO Update information
     */
    void updateCartSelected(Long userId, @Valid AppCartUpdateSelectedReqVO updateSelectedReqVO);

    /**
     * Reset shopping cart items
     *
     * Usage scenarios：The corresponding product in a shopping cart item is invalid（For example SPU Removed from shelves），You can reselect the corresponding one SKU
     *
     * @param userId User Number
     * @param updateReqVO Reset information
     */
    void resetCart(Long userId, AppCartResetReqVO updateReqVO);

    /**
     * Delete shopping cart items
     *
     * @param userId User Number
     * @param ids The number of the shopping item
     */
    void deleteCart(Long userId, Collection<Long> ids);

    /**
     * Query the number of items in the user's shopping cart
     *
     * @param userId User Number
     * @return Quantity of goods
     */
    Integer getCartCount(Long userId);

    /**
     * Query the user's shopping cart list
     *
     * @param userId User Number
     * @return Shopping cart list
     */
    AppCartListRespVO getCartList(Long userId);

    /**
     * Query the user's shopping cart list
     *
     * @param userId User Number
     * @param ids The number of the shopping item
     * @return Shopping cart list
     */
    List<CartDO> getCartList(Long userId, Set<Long> ids);

}
