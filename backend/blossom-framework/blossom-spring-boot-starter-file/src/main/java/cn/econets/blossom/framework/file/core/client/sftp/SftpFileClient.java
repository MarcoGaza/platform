package cn.econets.blossom.framework.file.core.client.sftp;

import cn.econets.blossom.framework.common.util.io.FileUtils;
import cn.econets.blossom.framework.file.core.client.AbstractFileClient;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ssh.Sftp;

import java.io.File;

/**
 * Sftp File Client
 *
 */
public class SftpFileClient extends AbstractFileClient<SftpFileClientConfig> {

    private Sftp sftp;

    public SftpFileClient(Long id, SftpFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // Completion style。For example Linux Yes /，Windows Yes \
        if (!config.getBasePath().endsWith(File.separator)) {
            config.setBasePath(config.getBasePath() + File.separator);
        }
        // Initialization Ftp Object
        this.sftp = new Sftp(config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
    }

    @Override
    public String upload(byte[] content, String path, String type) {
        // Execute write
        String filePath = getFilePath(path);
        File file = FileUtils.createTempFile(content);
        sftp.upload(filePath, file);
        // Splicing return path
        return super.formatFileUrl(config.getDomain(), path);
    }

    @Override
    public void delete(String path) {
        String filePath = getFilePath(path);
        sftp.delFile(filePath);
    }

    @Override
    public byte[] getContent(String path) {
        String filePath = getFilePath(path);
        File destFile = FileUtils.createTempFile();
        sftp.download(filePath, destFile);
        return FileUtil.readBytes(destFile);
    }

    private String getFilePath(String path) {
        return config.getBasePath() + path;
    }

}
