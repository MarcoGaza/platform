package cn.econets.blossom.module.product.dal.dataobject.favorite;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Product Collection DO
 *
 */
@TableName("product_favorite")
@KeySequence("product_favorite_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFavoriteDO extends BaseDO {

    /**
     * Number，Primary key auto-increment
     */
    @TableId
    private Long id;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Number
     */
    private Long userId;
    /**
     * Products SPU Number
     *
     * Relationship {@link ProductSpuDO#getId()}
     */
    private Long spuId;

}
