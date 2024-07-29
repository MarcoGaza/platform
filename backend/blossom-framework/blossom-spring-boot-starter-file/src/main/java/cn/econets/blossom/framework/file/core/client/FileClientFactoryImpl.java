package cn.econets.blossom.framework.file.core.client;

import cn.econets.blossom.framework.file.core.enums.FileStorageEnum;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * File client factory implementation class
 *
 */
@Slf4j
public class FileClientFactoryImpl implements FileClientFactory {

    /**
     * File Client Map
     * key：Configuration number
     */
    private final ConcurrentMap<Long, AbstractFileClient<?>> clients = new ConcurrentHashMap<>();

    @Override
    public FileClient getFileClient(Long configId) {
        AbstractFileClient<?> client = clients.get(configId);
        if (client == null) {
            log.error("[getFileClient][Configuration number({}) Client not found]", configId);
        }
        return client;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Config extends FileClientConfig> void createOrUpdateFileClient(Long configId, Integer storage, Config config) {
        AbstractFileClient<Config> client = (AbstractFileClient<Config>) clients.get(configId);
        if (client == null) {
            client = this.createFileClient(configId, storage, config);
            client.init();
            clients.put(client.getId(), client);
        } else {
            client.refresh(config);
        }
    }

    @SuppressWarnings("unchecked")
    private <Config extends FileClientConfig> AbstractFileClient<Config> createFileClient(
            Long configId, Integer storage, Config config) {
        FileStorageEnum storageEnum = FileStorageEnum.getByStorage(storage);
        Assert.notNull(storageEnum, String.format("File Configuration(%s) Empty", storageEnum));
        // Create client
        return (AbstractFileClient<Config>) ReflectUtil.newInstance(storageEnum.getClientClass(), configId, config);
    }

}
