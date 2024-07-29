package cn.econets.blossom.module.product.dal.dataobject.comment;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Product Reviews DO
 *
 */
@TableName(value = "product_comment", autoResultMap = true)
@KeySequence("product_comment_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentDO extends BaseDO {

    /**
     * Default anonymous nickname
     */
    public static final String NICKNAME_ANONYMOUS = "Anonymous user";

    /**
     * Comment number，Primary key auto-increment
     */
    @TableId
    private Long id;

    /**
     * The user ID of the reviewer
     *
     * Relationship MemberUserDO of id Number
     */
    private Long userId;
    /**
     * Name of the reviewer
     */
    private String userNickname;
    /**
     * Evaluator's avatar
     */
    private String userAvatar;
    /**
     * Is it anonymous?
     */
    private Boolean anonymous;

    /**
     * Transaction order number
     *
     * Relationship TradeOrderDO of id Number
     */
    private Long orderId;
    /**
     * Transaction order item number
     *
     * Relationship TradeOrderItemDO of id Number
     */
    private Long orderItemId;

    /**
     * Products SPU Number
     *
     * Relationship {@link ProductSpuDO#getId()}
     */
    private Long spuId;
    /**
     * Products SPU Name
     *
     * Association {@link ProductSpuDO#getName()}
     */
    private String spuName;
    /**
     * Products SKU Number
     *
     * Relationship {@link ProductSkuDO#getId()}
     */
    private Long skuId;
    /**
     * Products SKU Image address
     *
     * Relationship {@link ProductSkuDO#getPicUrl()}
     */
    private String skuPicUrl;
    /**
     * Attribute array，JSON Format
     *
     * Relationship {@link ProductSkuDO#getProperties()}
     */
    @TableField(typeHandler = ProductSkuDO.PropertyTypeHandler.class)
    private List<ProductSkuDO.Property> skuProperties;

    /**
     * Is it visible?
     *
     * true:Show
     * false:Hide
     */
    private Boolean visible;
    /**
     * Rating Stars
     *
     * 1-5 Points
     */
    private Integer scores;
    /**
     * Description star rating
     *
     * 1-5 Star
     */
    private Integer descriptionScores;
    /**
     * Service Stars
     *
     * 1-5 Star
     */
    private Integer benefitScores;
    /**
     * Comment content
     */
    private String content;
    /**
     * Comment picture address array
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> picUrls;

    /**
     * Did the merchant reply?
     */
    private Boolean replyStatus;
    /**
     * Reply to the administrator number
     * Relationship AdminUserDO of id Number
     */
    private Long replyUserId;
    /**
     * Merchant's reply content
     */
    private String replyContent;
    /**
     * Merchant response time
     */
    private LocalDateTime replyTime;

}
