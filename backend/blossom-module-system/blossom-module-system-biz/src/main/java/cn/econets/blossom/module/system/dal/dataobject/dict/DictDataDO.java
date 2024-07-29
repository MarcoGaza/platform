package cn.econets.blossom.module.system.dal.dataobject.dict;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Dictionary data table
 *
 */
@TableName("system_dict_data")
@KeySequence("system_dict_data_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataDO extends BaseDO {

    /**
     * Dictionary data number
     */
    @TableId
    private Long id;
    /**
     * Dictionary sort
     */
    private Integer sort;
    /**
     * Dictionary tags
     */
    private String label;
    /**
     * Dictionary value
     */
    private String value;
    /**
     * Dictionary type
     *
     * Redundant {@link DictDataDO#getDictType()}
     */
    private String dictType;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Color type
     *
     * Corresponding to element-ui for default、primary、success、info、warning、danger
     */
    private String colorType;
    /**
     * css Style
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String cssClass;
    /**
     * Remarks
     */
    private String remark;

}
