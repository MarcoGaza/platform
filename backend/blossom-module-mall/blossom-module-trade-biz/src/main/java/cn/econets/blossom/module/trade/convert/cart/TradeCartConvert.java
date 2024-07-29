package cn.econets.blossom.module.trade.convert.cart;

import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.product.enums.spu.ProductSpuStatusEnum;
import cn.econets.blossom.module.trade.controller.app.base.sku.AppProductSkuBaseRespVO;
import cn.econets.blossom.module.trade.controller.app.base.spu.AppProductSpuBaseRespVO;
import cn.econets.blossom.module.trade.controller.app.cart.vo.AppCartListRespVO;
import cn.econets.blossom.module.trade.dal.dataobject.cart.CartDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;

@Mapper
public interface TradeCartConvert {

    TradeCartConvert INSTANCE = Mappers.getMapper(TradeCartConvert.class);

    default AppCartListRespVO convertList(List<CartDO> carts,
                                          List<ProductSpuRespDTO> spus, List<ProductSkuRespDTO> skus) {
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(spus, ProductSpuRespDTO::getId);
        Map<Long, ProductSkuRespDTO> skuMap = convertMap(skus, ProductSkuRespDTO::getId);
        // Traverse，Start conversion
        List<AppCartListRespVO.Cart> validList = new ArrayList<>(carts.size());
        List<AppCartListRespVO.Cart> invalidList = new ArrayList<>();
        carts.forEach(cart -> {
            AppCartListRespVO.Cart cartVO = new AppCartListRespVO.Cart();
            cartVO.setId(cart.getId()).setCount(cart.getCount()).setSelected(cart.getSelected());
            ProductSpuRespDTO spu = spuMap.get(cart.getSpuId());
            ProductSkuRespDTO sku = skuMap.get(cart.getSkuId());
            cartVO.setSpu(convert(spu)).setSku(convert(sku));
            // If SPU Does not exist，Or remove from shelf，Or insufficient inventory，The description is invalid
            if (spu == null
                || !ProductSpuStatusEnum.isEnable(spu.getStatus())
                || spu.getStock() <= 0) {
                cartVO.setSelected(false); // Force to be unselectable
                invalidList.add(cartVO);
            } else {
                // Although SKU Maybe it won't exist，But you can reselect through the shopping cart
                validList.add(cartVO);
            }
        });
        return new AppCartListRespVO().setValidList(validList).setInvalidList(invalidList);
    }
    AppProductSpuBaseRespVO convert(ProductSpuRespDTO spu);
    AppProductSkuBaseRespVO convert(ProductSkuRespDTO sku);

}
