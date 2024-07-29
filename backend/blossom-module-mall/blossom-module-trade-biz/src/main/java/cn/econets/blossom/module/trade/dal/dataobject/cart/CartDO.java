package cn.econets.blossom.module.trade.dal.dataobject.cart;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Product information in the shopping cart DO
 *
 * Each product，Corresponding to a record，Passed {@link #spuId} Japanese {@link #skuId} Relationship
 *
 */
@TableName("trade_cart")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CartDO extends BaseDO {

    // ========= Basic fields BEGIN =========

    /**
     * Number，Only self-increment
     */
    private Long id;

    /**
     * User Number
     *
     * Relationship MemberUserDO of id Number
     */
    private Long userId;

    // ========= Product Information =========

    /**
     * Products SPU Number
     *
     * Relationship ProductSpuDO of id Number
     */
    private Long spuId;
    /**
     * Products SKU Number
     *
     * Relationship ProductSkuDO of id Number
     */
    private Long skuId;
    /**
     * Quantity of goods purchased
     */
    private Integer count;
    /**
     * Is it selected?
     */
    private Boolean selected;

}
