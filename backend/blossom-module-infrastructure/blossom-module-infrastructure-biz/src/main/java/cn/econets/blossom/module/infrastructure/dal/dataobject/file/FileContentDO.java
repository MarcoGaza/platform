package cn.econets.blossom.module.infrastructure.dal.dataobject.file;

import cn.econets.blossom.framework.file.core.client.db.DBFileClient;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * File Contents Table
 *
 * Specially used for storage {@link DBFileClient} File contents
 *
 */
@TableName("infra_file_content")
@KeySequence("infra_file_content_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileContentDO extends BaseDO {

    /**
     * Number，Database auto-increment
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * Configuration number
     *
     * Relationship {@link FileConfigDO#getId()}
     */
    private Long configId;
    /**
     * Path，That is, the file name
     */
    private String path;
    /**
     * File contents
     */
    private byte[] content;

}
