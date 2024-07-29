package cn.econets.blossom.module.system.dal.dataobject.dict;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Dictionary type table
 *
 */
@TableName("system_dict_type")
@KeySequence("system_dict_type_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictTypeDO extends BaseDO {

    /**
     * Dictionary primary key
     */
    @TableId
    private Long id;
    /**
     * Dictionary name
     */
    private String name;
    /**
     * Dictionary type
     */
    private String type;
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

    /**
     * Delete time
     */
    private LocalDateTime deletedTime;

}
