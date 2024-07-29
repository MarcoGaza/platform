package cn.econets.blossom.module.promotion.dal.dataobject.banner;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.promotion.enums.banner.BannerPositionEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * banner DO
 *
 *
 */
@TableName("promotion_banner")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDO extends BaseDO {

    /**
     * Number
     */
    private Long id;
    /**
     * Title
     */
    private String title;
    /**
     * Jump link
     */
    private String url;
    /**
     * Image link
     */
    private String picUrl;
    /**
     * Sort
     */
    private Integer sort;

    /**
     * Status {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * Positioning {@link BannerPositionEnum}
     */
    private Integer position;

    /**
     * Remarks
     */
    private String memo;

    /**
     * Number of clicks
     */
    private Integer browseCount;

}
