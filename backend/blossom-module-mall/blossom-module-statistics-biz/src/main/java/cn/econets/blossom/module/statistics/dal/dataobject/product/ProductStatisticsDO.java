package cn.econets.blossom.module.statistics.dal.dataobject.product;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * Product Statistics DO
 *
 */
@TableName("product_statistics")
@KeySequence("product_statistics_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatisticsDO extends BaseDO {

    /**
     * Number，Primary key auto-increment
     */
    @TableId
    private Long id;
    /**
     * Statistical date
     */
    private LocalDate time;
    /**
     * Products SPU Number
     */
    private Long spuId;
    /**
     * Views
     */
    private Integer browseCount;
    /**
     * Number of visitors
     */
    private Integer browseUserCount;
    /**
     * Number of favorites
     */
    private Integer favoriteCount;
    /**
     * Add to purchase quantity
     */
    private Integer cartCount;
    /**
     * Number of items ordered
     */
    private Integer orderCount;
    /**
     * Number of payments
     */
    private Integer orderPayCount;
    /**
     * Payment amount，Unit：Points
     */
    private Integer orderPayPrice;
    /**
     * Number of refunds
     */
    private Integer afterSaleCount;
    /**
     * Refund amount，Unit：Points
     */
    private Integer afterSaleRefundPrice;
    /**
     * Visitor payment conversion rate（Percentage）
     */
    private Integer browseConvertPercent;

}