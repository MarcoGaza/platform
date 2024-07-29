package cn.econets.blossom.module.member.dal.dataobject.address;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * User's mailing address DO
 *
 * 
 */
@TableName("member_address")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddressDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * User Number
     */
    private Long userId;
    /**
     * Recipient Name
     */
    private String name;
    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * Region Code
     */
    private Long areaId;
    /**
     * Detailed address of delivery
     */
    private String detailAddress;
    /**
     * Whether default
     *
     * true - Default recipient address
     */
    private Boolean defaultStatus;

}
