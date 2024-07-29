package cn.econets.blossom.module.infrastructure.service.file;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.io.FileUtils;
import cn.econets.blossom.framework.file.core.client.FileClient;
import cn.econets.blossom.framework.file.core.utils.FileTypeUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.file.vo.file.FilePageReqVO;
import cn.econets.blossom.module.infrastructure.dal.mysql.file.FileMapper;
import cn.econets.blossom.module.infrastructure.dal.dataobject.file.FileDO;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.FILE_NOT_EXISTS;

/**
 * File Service Implementation class
 *
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileConfigService fileConfigService;

    @Resource
    private FileMapper fileMapper;

    @Override
    public PageResult<FileDO> getFilePage(FilePageReqVO pageReqVO) {
        return fileMapper.selectPage(pageReqVO);
    }

    @Override
    @SneakyThrows
    public String createFile(String name, String path, byte[] content) {
        // Calculate default path Name
        String type = FileTypeUtils.getMineType(content, name);
        if (StrUtil.isEmpty(path)) {
            path = FileUtils.generatePath(content, name);
        }
        // If name Empty，Then use path Fill
        if (StrUtil.isEmpty(name)) {
            name = path;
        }

        // Upload to file storage
        FileClient client = fileConfigService.getMasterFileClient();
        Assert.notNull(client, "Client(master) Cannot be empty");
        String url = client.upload(content, path, type);

        // Save to database
        FileDO file = new FileDO();
        file.setConfigId(client.getId());
        file.setName(name);
        file.setPath(path);
        file.setUrl(url);
        file.setType(type);
        file.setSize(content.length);
        fileMapper.insert(file);
        return url;
    }

    @Override
    public void deleteFile(Long id) throws Exception {
        // Check existence
        FileDO file = validateFileExists(id);

        // Delete from file storage
        FileClient client = fileConfigService.getFileClient(file.getConfigId());
        Assert.notNull(client, "Client({}) Cannot be empty", file.getConfigId());
        client.delete(file.getPath());

        // Delete record
        fileMapper.deleteById(id);
    }

    private FileDO validateFileExists(Long id) {
        FileDO fileDO = fileMapper.selectById(id);
        if (fileDO == null) {
            throw exception(FILE_NOT_EXISTS);
        }
        return fileDO;
    }

    @Override
    public byte[] getFileContent(Long configId, String path) throws Exception {
        FileClient client = fileConfigService.getFileClient(configId);
        Assert.notNull(client, "Client({}) Cannot be empty", configId);
        return client.getContent(path);
    }

}
