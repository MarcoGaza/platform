package cn.econets.blossom.module.product.dal.dataobject.property;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


/**
 * Product attribute value DO
 *
 */
@TableName("product_property_value")
@KeySequence("product_property_value_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPropertyValueDO extends BaseDO {

    /**
     * SPU Single specification，Default property value id
     */
    public static final Long ID_DEFAULT = 0L;
    /**
     * SPU Single specification，Default property value name
     */
    public static final String NAME_DEFAULT = "Default";

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * The number of the property item
     *
     * Relationship {@link ProductPropertyDO#getId()}
     */
    private Long propertyId;
    /**
     * Name
     */
    private String name;
    /**
     * Remarks
     *
     */
    private String remark;

}
