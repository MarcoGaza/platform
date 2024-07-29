package cn.econets.blossom.module.infrastructure.api.file;

import cn.econets.blossom.module.infrastructure.service.file.FileService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * File API Implementation class
 *
 */
@Service
@Validated
public class FileApiImpl implements FileApi {

    @Resource
    private FileService fileService;

    @Override
    public String createFile(String name, String path, byte[] content) {
        return fileService.createFile(name, path, content);
    }

}
