package cn.econets.blossom.module.infrastructure.dal.dataobject.config;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.infrastructure.enums.config.ConfigTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Parameter configuration table
 *
 */
@TableName("infra_config")
@KeySequence("infra_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConfigDO extends BaseDO {

    /**
     * Parameter primary key
     */
    @TableId
    private Long id;
    /**
     * Parameter classification
     */
    private String category;
    /**
     * Parameter name
     */
    private String name;
    /**
     * Parameter key name
     *
     * Support multiple DB Type，Cannot be used directly key + @TableField("config_key") To achieve conversion，The reason is "config_key" AS key An error occurs
     */
    private String configKey;
    /**
     * Parameter key value
     */
    private String value;
    /**
     * Parameter type
     *
     * Enumeration {@link ConfigTypeEnum}
     */
    private Integer type;
    /**
     * Is it visible?
     *
     * Invisible parameters，Generally sensitive parameters，Front end cannot be obtained
     */
    private Boolean visible;
    /**
     * Remarks
     */
    private String remark;

}
