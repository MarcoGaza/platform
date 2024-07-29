package cn.econets.blossom.module.product.dal.dataobject.category;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Product Categories DO
 *
 */
@TableName("product_category")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDO extends BaseDO {

    /**
     * Parent category number - Root category
     */
    public static final Long PARENT_ID_NULL = 0L;
    /**
     * Limit classification level
     */
    public static final int CATEGORY_LEVEL = 2;

    /**
     * Classification number
     */
    @TableId
    private Long id;
    /**
     * Parent category number
     */
    private Long parentId;
    /**
     * Category Name
     */
    private String name;
    /**
     * Mobile terminal classification map
     *
     * Suggestions 180*180 Resolution
     */
    private String picUrl;
    /**
     * Category sorting
     */
    private Integer sort;
    /**
     * Open status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
