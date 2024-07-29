package cn.econets.blossom.module.crm.dal.dataobject.business;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Business opportunity product association table DO
 *
 */
@TableName("crm_business_product")
@KeySequence("crm_business_product_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmBusinessProductDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Opportunity Number
     *
     * Relationship {@link CrmBusinessDO#getId()}
     */
    private Long businessId;
    /**
     * Product Number
     *
     * Relationship {@link CrmProductDO#getId()}
     */
    private Long productId;
    /**
     * Product price
     */
    private Integer price;
    /**
     * Sales Price, Unit：Points
     */
    private Integer salesPrice;
    /**
     * Quantity
     */
    private Integer count;
    /**
     * Discount
     */
    private Integer discountPercent;
    /**
     * Total price（Discounted price）
     */
    private Integer totalPrice;

}
