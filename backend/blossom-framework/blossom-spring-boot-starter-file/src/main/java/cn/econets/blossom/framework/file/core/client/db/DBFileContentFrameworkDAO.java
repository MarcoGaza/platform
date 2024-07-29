package cn.econets.blossom.framework.file.core.client.db;

/**
 * File contents Framework DAO Interface
 *
 */
public interface DBFileContentFrameworkDAO {

    /**
     * Insert file content
     *
     * @param configId Configuration number
     * @param path Path
     * @param content Content
     */
    void insert(Long configId, String path, byte[] content);

    /**
     * Delete file contents
     *
     * @param configId Configuration number
     * @param path Path
     */
    void delete(Long configId, String path);

    /**
     * Get file content
     *
     * @param configId Configuration number
     * @param path Path
     * @return Content
     */
    byte[] selectContent(Long configId, String path);

}
