package cn.econets.blossom.module.system.dal.dataobject.dept;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Position List
 *
 */
@TableName("system_post")
@KeySequence("system_post_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDO extends BaseDO {

    /**
     * Position number
     */
    @TableId
    private Long id;
    /**
     * Position Name
     */
    private String name;
    /**
     * Position Code
     */
    private String code;
    /**
     * Position ranking
     */
    private Integer sort;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Remarks
     */
    private String remark;

}
