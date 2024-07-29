package cn.econets.blossom.module.product.dal.dataobject.history;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Product browsing history DO
 *
 */
@TableName("product_browse_history")
@KeySequence("product_browse_history_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBrowseHistoryDO extends BaseDO {

    /**
     * Record number
     */
    @TableId
    private Long id;
    /**
     * Products SPU Number
     */
    private Long spuId;
    /**
     * User Number
     */
    private Long userId;
    /**
     * Whether to delete the user
     */
    private Boolean userDeleted;

}