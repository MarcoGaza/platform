package cn.econets.blossom.module.infrastructure.dal.dataobject.file;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * File List
 * Every time a file is uploaded，Will record a record in this table
 *
 */
@TableName("infra_file")
@KeySequence("infra_file_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDO extends BaseDO {

    /**
     * Number，Database auto-increment
     */
    private Long id;
    /**
     * Configuration number
     *
     * Relationship {@link FileConfigDO#getId()}
     */
    private Long configId;
    /**
     * Original file name
     */
    private String name;
    /**
     * Path，That is, the file name
     */
    private String path;
    /**
     * Access address
     */
    private String url;
    /**
     * File MIME Type，For example "application/octet-stream"
     */
    private String type;
    /**
     * File size
     */
    private Integer size;

}
