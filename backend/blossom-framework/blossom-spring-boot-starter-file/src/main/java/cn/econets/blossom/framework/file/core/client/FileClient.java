package cn.econets.blossom.framework.file.core.client;

/**
 * File Client
 *
 */
public interface FileClient {

    /**
     * Get client ID
     *
     * @return Client ID
     */
    Long getId();

    /**
     * Upload file
     *
     * @param content File stream
     * @param path Relative path
     * @return Full path，That is HTTP Access address
     * @throws Exception When uploading files，Throw Exception Abnormal
     */
    String upload(byte[] content, String path, String type) throws  Exception;

    /**
     * Delete file
     *
     * @param path Relative path
     * @throws Exception When deleting files，Throw Exception Abnormal
     */
    void delete(String path) throws Exception;

    /**
     * Get the contents of the file
     *
     * @param path Relative path
     * @return Contents of the file
     */
    byte[] getContent(String path) throws Exception;

}
