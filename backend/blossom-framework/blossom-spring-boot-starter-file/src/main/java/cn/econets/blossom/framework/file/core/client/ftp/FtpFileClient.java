package cn.econets.blossom.framework.file.core.client.ftp;

import cn.econets.blossom.framework.file.core.client.AbstractFileClient;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpException;
import cn.hutool.extra.ftp.FtpMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Ftp File Client
 *
 */
public class FtpFileClient extends AbstractFileClient<FtpFileClientConfig> {

    private Ftp ftp;

    public FtpFileClient(Long id, FtpFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // Configure \ Replace with /, If the path is configured \a\test, Replace with /a/test, Replacement method has been processed null Situation
        config.setBasePath(StrUtil.replace(config.getBasePath(), StrUtil.BACKSLASH, StrUtil.SLASH));
        // ftpThe path is / End
        if (!config.getBasePath().endsWith(StrUtil.SLASH)) {
            config.setBasePath(config.getBasePath() + StrUtil.SLASH);
        }
        // Initialization Ftp Object
        this.ftp = new Ftp(config.getHost(), config.getPort(), config.getUsername(), config.getPassword(),
                CharsetUtil.CHARSET_UTF_8, null, null, FtpMode.valueOf(config.getMode()));
    }

    @Override
    public String upload(byte[] content, String path, String type) {
        // Execute write
        String filePath = getFilePath(path);
        String fileName = FileUtil.getName(filePath);
        String dir = StrUtil.removeSuffix(filePath, fileName);
        ftp.reconnectIfTimeout();
        boolean success = ftp.upload(dir, fileName, new ByteArrayInputStream(content));
        if (!success) {
            throw new FtpException(StrUtil.format("Upload files to the target directory ({}) Failed", filePath));
        }
        // Splicing return path
        return super.formatFileUrl(config.getDomain(), path);
    }

    @Override
    public void delete(String path) {
        String filePath = getFilePath(path);
        ftp.reconnectIfTimeout();
        ftp.delFile(filePath);
    }

    @Override
    public byte[] getContent(String path) {
        String filePath = getFilePath(path);
        String fileName = FileUtil.getName(filePath);
        String dir = StrUtil.removeSuffix(filePath, fileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ftp.reconnectIfTimeout();
        ftp.download(dir, fileName, out);
        return out.toByteArray();
    }

    private String getFilePath(String path) {
        return config.getBasePath() + path;
    }

}
