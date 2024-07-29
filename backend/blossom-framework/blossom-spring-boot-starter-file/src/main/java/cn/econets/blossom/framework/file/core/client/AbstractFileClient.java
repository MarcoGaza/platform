package cn.econets.blossom.framework.file.core.client;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class for file client，Provide template method，Reduce redundant code in subclasses
 *
 */
@Slf4j
public abstract class AbstractFileClient<Config extends FileClientConfig> implements FileClient {

    /**
     * Configuration number
     */
    private final Long id;
    /**
     * File configuration
     */
    protected Config config;

    public AbstractFileClient(Long id, Config config) {
        this.id = id;
        this.config = config;
    }

    /**
     * Initialization
     */
    public final void init() {
        doInit();
        log.debug("[init][Configuration({}) Initialization completed]", config);
    }

    /**
     * Custom initialization
     */
    protected abstract void doInit();

    public final void refresh(Config config) {
        // Judge whether to update
        if (config.equals(this.config)) {
            return;
        }
        log.info("[refresh][Configuration({})Changes have occurred，Reinitialize]", config);
        this.config = config;
        // Initialization
        this.init();
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Format file URL Access address
     * Usage scenarios：local、ftp、db，Passed FileController of getFile To get the file content
     *
     * @param domain Custom domain name
     * @param path File path
     * @return URL Access address
     */
    protected String formatFileUrl(String domain, String path) {
        return StrUtil.format("{}/admin-api/infra/file/{}/get/{}", domain, getId(), path);
    }

}
