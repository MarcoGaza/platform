package cn.econets.blossom.module.product.dal.dataobject.brand;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Product Brand DO
 *
 */
@TableName("product_brand")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBrandDO extends BaseDO {

    /**
     * Brand Number
     */
    @TableId
    private Long id;
    /**
     * Brand Name
     */
    private String name;
    /**
     * Brand picture
     */
    private String picUrl;
    /**
     * Brand sorting
     */
    private Integer sort;
    /**
     * Brand Description
     */
    private String description;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

    // TODO firstLetter First letter

}
