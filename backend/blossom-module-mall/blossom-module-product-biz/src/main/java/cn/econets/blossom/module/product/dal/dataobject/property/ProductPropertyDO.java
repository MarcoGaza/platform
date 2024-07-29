package cn.econets.blossom.module.product.dal.dataobject.property;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Product attribute item DO
 *
 */
@TableName("product_property")
@KeySequence("product_property_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPropertyDO extends BaseDO {

    /**
     * SPU Single specification，Default properties id
     */
    public static final Long ID_DEFAULT = 0L;
    /**
     * SPU Single specification，Default property name
     */
    public static final String NAME_DEFAULT = "Default";

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Name
     */
    private String name;
    /**
     * Status
     */
    private Integer status;
    /**
     * Remarks
     */
    private String remark;

}
