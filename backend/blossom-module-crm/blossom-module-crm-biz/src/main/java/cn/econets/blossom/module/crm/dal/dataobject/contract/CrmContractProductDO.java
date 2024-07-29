package cn.econets.blossom.module.crm.dal.dataobject.contract;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Contract product association table DO
 *
 */
@TableName("crm_contract_product")
@KeySequence("crm_contract_product_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmContractProductDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Product Number
     *
     * Relationship {@link CrmProductDO#getId()}
     */
    private Long productId;
    /**
     * Contract Number
     *
     * Relationship {@link CrmContractDO#getId()}
     */
    private Long contractId;
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
     *
     * TODO @puhui999：You can write down the calculation formula；
     */
    private Integer totalPrice;

}
