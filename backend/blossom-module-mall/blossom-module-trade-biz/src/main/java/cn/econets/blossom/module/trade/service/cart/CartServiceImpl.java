package cn.econets.blossom.module.trade.service.cart;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.trade.controller.app.cart.vo.*;
import cn.econets.blossom.module.trade.convert.cart.TradeCartConvert;
import cn.econets.blossom.module.trade.dal.dataobject.cart.CartDO;
import cn.econets.blossom.module.trade.dal.mysql.cart.CartMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SKU_NOT_EXISTS;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SKU_STOCK_NOT_ENOUGH;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.CARD_ITEM_NOT_FOUND;
import static java.util.Collections.emptyList;

/**
 * Shopping Cart Service Implementation class
 *
 * // TODO Future Optimization：Calculate the price of the shopping cart，Support marketing information；Reason for not currently supported，The front-end interface requires a front-end pr Support；For example：Member Price；
 *
 */
@Service
@Validated
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;

    @Override
    public Long addCart(Long userId, AppCartAddReqVO addReqVO) {
        // Query TradeCartDO
        CartDO cart = cartMapper.selectByUserIdAndSkuId(userId, addReqVO.getSkuId());
        // Verification SKU
        Integer count = addReqVO.getCount();
        ProductSkuRespDTO sku = checkProductSku(addReqVO.getSkuId(), count);

        // Situation 1：Exists，Then update the quantity
        if (cart != null) {
            cartMapper.updateById(new CartDO().setId(cart.getId()).setSelected(true)
                    .setCount(cart.getCount() + count));
            return cart.getId();
        // Situation 2：Does not exist，Insert
        } else {
            cart = new CartDO().setUserId(userId).setSelected(true)
                    .setSpuId(sku.getSpuId()).setSkuId(sku.getId()).setCount(count);
            cartMapper.insert(cart);
        }
        return cart.getId();
    }

    @Override
    public void updateCartCount(Long userId, AppCartUpdateCountReqVO updateReqVO) {
        // Verification TradeCartDO Exists
        CartDO cart = cartMapper.selectById(updateReqVO.getId(), userId);
        if (cart == null) {
            throw exception(CARD_ITEM_NOT_FOUND);
        }
        // Check product SKU
        checkProductSku(cart.getSkuId(), updateReqVO.getCount());

        // Update quantity
        cartMapper.updateById(new CartDO().setId(cart.getId())
                .setCount(updateReqVO.getCount()));
    }

    @Override
    public void updateCartSelected(Long userId, AppCartUpdateSelectedReqVO updateSelectedReqVO) {
        cartMapper.updateByIds(updateSelectedReqVO.getIds(), userId,
                new CartDO().setSelected(updateSelectedReqVO.getSelected()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetCart(Long userId, AppCartResetReqVO resetReqVO) {
        // First step：Delete the original shopping item
        CartDO oldCart = cartMapper.selectById(resetReqVO.getId(), userId);
        if (oldCart == null) {
            throw exception(CARD_ITEM_NOT_FOUND);
        }
        cartMapper.deleteById(oldCart.getId());

        // Step 2：Add new shopping item
        CartDO newCart = cartMapper.selectByUserIdAndSkuId(userId, resetReqVO.getSkuId());
        if (newCart != null) {
            updateCartCount(userId, new AppCartUpdateCountReqVO()
                    .setId(newCart.getId()).setCount(resetReqVO.getCount()));
        } else {
            addCart(userId, new AppCartAddReqVO().setSkuId(resetReqVO.getSkuId())
                    .setCount(resetReqVO.getCount()));
        }
    }

    /**
     * Delete items from shopping cart
     *
     * @param userId User Number
     * @param ids Products SKU Numbered array
     */
    @Override
    public void deleteCart(Long userId, Collection<Long> ids) {
        // Query TradeCartDO List
        List<CartDO> carts = cartMapper.selectListByIds(ids, userId);
        if (CollUtil.isEmpty(carts)) {
            return;
        }

        // Batch mark deletion
        cartMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer getCartCount(Long userId) {
        // TODO Need to be included selected
        return cartMapper.selectSumByUserId(userId);
    }

    @Override
    public AppCartListRespVO getCartList(Long userId) {
        // Get the items in the shopping cart
        List<CartDO> carts = cartMapper.selectListByUserId(userId);
        carts.sort(Comparator.comparing(CartDO::getId).reversed());
        // If not empty，Returns empty result
        if (CollUtil.isEmpty(carts)) {
            return new AppCartListRespVO().setValidList(emptyList())
                    .setInvalidList(emptyList());
        }

        // Query SPU、SKU List
        List<ProductSpuRespDTO> spus = productSpuApi.getSpuList(convertSet(carts, CartDO::getSpuId));
        List<ProductSkuRespDTO> skus = productSkuApi.getSkuList(convertSet(carts, CartDO::getSkuId));

        // If SPU Deleted，Delete the corresponding product in the shopping cart。Delayed deletion
        // Why not SKU Deleted？Because SKU When deleted，You can still pass SPU Select other SKU
        deleteCartIfSpuDeleted(carts, spus);

        // Splicing data
        return TradeCartConvert.INSTANCE.convertList(carts, spus, skus);
    }

    @Override
    public List<CartDO> getCartList(Long userId, Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return cartMapper.selectListByUserId(userId, ids);
    }

    private void deleteCartIfSpuDeleted(List<CartDO> carts, List<ProductSpuRespDTO> spus) {
        // If SPU Deleted，Delete the corresponding product in the shopping cart。Delayed deletion
        carts.removeIf(cart -> {
            if (spus.stream().noneMatch(spu -> spu.getId().equals(cart.getSpuId()))) {
                cartMapper.deleteById(cart.getId());
                return true;
            }
            return false;
        });
    }

    /**
     * Check product SKU Is it legal?
     * 1. Does it exist?
     * 2. Whether to remove from shelf
     * 3. Insufficient inventory
     *
     * @param skuId Products SKU Number
     * @param count Quantity of goods
     * @return Products SKU
     */
    private ProductSkuRespDTO checkProductSku(Long skuId, Integer count) {
        ProductSkuRespDTO sku = productSkuApi.getSku(skuId);
        if (sku == null) {
            throw exception(SKU_NOT_EXISTS);
        }
        if (count > sku.getStock()) {
            throw exception(SKU_STOCK_NOT_ENOUGH);
        }
        return sku;
    }

}
