package cn.econets.blossom.module.infrastructure.dal.dataobject.file;

import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import cn.econets.blossom.framework.file.core.enums.FileStorageEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

/**
 * File configuration table
 *
 */
@TableName(value = "infra_file_config", autoResultMap = true)
@KeySequence("infra_file_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileConfigDO extends BaseDO {

    /**
     * Configuration number，Database auto-increment
     */
    private Long id;
    /**
     * Configuration name
     */
    private String name;
    /**
     * Memory
     *
     * Enumeration {@link FileStorageEnum}
     */
    private Integer storage;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Whether to be the main configuration
     *
     * Since we can configure multiple file configurations，By default，Use the main configuration to upload files
     */
    private Boolean master;

    /**
     * Payment channel configuration
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private FileClientConfig config;

}
