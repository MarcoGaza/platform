package cn.econets.blossom.framework.file.core.client.db;

import cn.econets.blossom.framework.file.core.client.AbstractFileClient;
import cn.hutool.extra.spring.SpringUtil;

/**
 * Based on DB Configuration class for the stored file client
 *
 */
public class DBFileClient extends AbstractFileClient<DBFileClientConfig> {

    private DBFileContentFrameworkDAO dao;

    public DBFileClient(Long id, DBFileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
    }

    @Override
    public String upload(byte[] content, String path, String type) {
        getDao().insert(getId(), path, content);
        // Splicing return path
        return super.formatFileUrl(config.getDomain(), path);
    }

    @Override
    public void delete(String path) {
        getDao().delete(getId(), path);
    }

    @Override
    public byte[] getContent(String path) {
        return getDao().selectContent(getId(), path);
    }

    private DBFileContentFrameworkDAO getDao() {
        // Delayed acquisition，Because SpringUtil Initialization is too slow
        if (dao == null) {
            dao = SpringUtil.getBean(DBFileContentFrameworkDAO.class);
        }
        return dao;
    }

}
