package cn.econets.blossom.framework.file.core.client;

import cn.econets.blossom.framework.file.core.enums.FileStorageEnum;

public interface FileClientFactory {

    /**
     * Get file client
     *
     * @param configId Configuration number
     * @return File Client
     */
    FileClient getFileClient(Long configId);

    /**
     * Create a file client
     *
     * @param configId Configuration number
     * @param storage Memory enumeration {@link FileStorageEnum}
     * @param config File Configuration
     */
    <Config extends FileClientConfig> void createOrUpdateFileClient(Long configId, Integer storage, Config config);

}
