package cn.econets.blossom.module.crm.dal.dataobject.product;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.enums.DictTypeConstants;
import cn.econets.blossom.module.crm.enums.product.CrmProductStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CRM Products DO
 *
 */
@TableName("crm_product")
@KeySequence("crm_product_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmProductDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Product Name
     */
    private String name;
    /**
     * Product Code
     */
    private String no;
    /**
     * Unit
     *
     * Dictionary {@link DictTypeConstants#CRM_PRODUCT_UNIT}
     */
    private Integer unit;
    /**
     * Price，Unit：Points
     */
    private Integer price;
    /**
     * Status
     *
     * Relationship {@link CrmProductStatusEnum}
     */
    private Integer status;
    /**
     * Product Category ID
     *
     * Relationship {@link CrmProductCategoryDO#getId()} Field
     */
    private Long categoryId;
    /**
     * Product Description
     */
    private String description;
    /**
     * User ID of the person in charge
     *
     * Relationship AdminUserDO of id Field
     */
    private Long ownerUserId;

}
