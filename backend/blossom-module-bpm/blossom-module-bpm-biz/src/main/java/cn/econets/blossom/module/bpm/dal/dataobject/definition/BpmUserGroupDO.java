package cn.econets.blossom.module.bpm.dal.dataobject.definition;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.JsonLongSetTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Set;

/**
 * Bpm User Group
 *
 */
@TableName(value = "bpm_user_group", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmUserGroupDO extends BaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * Group name
     */
    private String name;
    /**
     * Description
     */
    private String description;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Member user ID array
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> memberUserIds;

}
