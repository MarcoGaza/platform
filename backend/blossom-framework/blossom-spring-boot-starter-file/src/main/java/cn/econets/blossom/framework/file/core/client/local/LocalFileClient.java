package cn.econets.blossom.framework.file.core.client.local;

import cn.econets.blossom.framework.file.core.client.AbstractFileClient;
import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * Local file client
 *
 */
public class LocalFileClient extends AbstractFileClient<LocalFileClientConfig> {

    public LocalFileClient(Long id, LocalFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // Completion style。For example Linux Yes /，Windows Yes \
        if (!config.getBasePath().endsWith(File.separator)) {
            config.setBasePath(config.getBasePath() + File.separator);
        }
    }

    @Override
    public String upload(byte[] content, String path, String type) {
        // Execute write
        String filePath = getFilePath(path);
        FileUtil.writeBytes(content, filePath);
        // Splicing return path
        return super.formatFileUrl(config.getDomain(), path);
    }

    @Override
    public void delete(String path) {
        String filePath = getFilePath(path);
        FileUtil.del(filePath);
    }

    @Override
    public byte[] getContent(String path) {
        String filePath = getFilePath(path);
        return FileUtil.readBytes(filePath);
    }

    private String getFilePath(String path) {
        return config.getBasePath() + path;
    }

}
